/**
 * Created by Administrator on 2014/12/15.
 * 成绩与图书馆进出次数相关展示
 */
NS.define('Pages.sc.ykt.Demo4Ana1',{
    extend : 'Template.Page',
    requires : [],
    cssRequires : ['app/pages/sc/template/css/xgxfx.css'],
    modelConfig : {
        serviceConfig : {
            queryChart:'schoolBasicSituationService?queryChart'	//查询基本报表情况
        }
    },
    tplRequires : [],
    w : 1000,
    h : 600,
    r : 600,
    init : function(){
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'学生成绩与出入图书馆次数相关性分析',
                pageHelpInfo:'学生成绩与出入图书馆次数相关性分析,在下图中年份上向前、向后移动鼠标改变统计的年份'}
        });
        var chartDiv = new Ext.Component({
            tpl : '<p id="chart" style="margin-bottom: 20px;"></p>',
            data : []
        });
        var page = this.page = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,chartDiv]
        });
        this.setPageComponent(page);
        page.on('afterrender',function(){
            this.w = chartDiv.getWidth();
            this.draw();
        },this);
    },
    draw : function(){
        // Various accessors that specify the four dimensions of data to visualize.
        function x(d) { return d.income; }// x轴 收入 成绩排名
        function y(d) { return d.lifeExpectancy; }// y轴 寿命 出入次数
        function radius(d) { return d.population; }// 半径 人口 人数
        function color(d) { return d.region; }// 颜色 地域
        function key(d) { return d.name; }// 名称 学号

        // Chart dimensions.
        var margin = {top: 19.5, right: 19.5, bottom: 19.5, left: 29.5},
            width = this.w-margin.right-margin.left,
            height = 500 - margin.top - margin.bottom;

        // Various scales. These domains make assumptions of data, naturally.
        var xScale = d3.scale.log().domain([300, 1e5]).range([0, width]),
            yScale = d3.scale.linear().domain([0, 100]).range([height, 0]),
            radiusScale = d3.scale.sqrt().domain([0, 5e8]).range([0, 40]),
            colorScale = d3.scale.category10();

        // The x & y axes.
        var xAxis = d3.svg.axis().orient("bottom").scale(xScale).ticks(12, d3.format(",d")),
            yAxis = d3.svg.axis().scale(yScale).orient("left");

        // Create the SVG container and set the origin.
        var svg = d3.select("#chart").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        // Add the x-axis.
        svg.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis);

        // Add the y-axis.
        svg.append("g")
            .attr("class", "y axis")
            .call(yAxis);

        // Add an x-axis label.
        svg.append("text")
            .attr("class", "x label")
            .attr("text-anchor", "end")
            .attr("x", width)
            .attr("y", height - 6)
            .text("学生在本学年的出入图书馆次数 (次)");

        // Add a y-axis label.
        svg.append("text")
            .attr("class", "y label")
            .attr("text-anchor", "end")
            .attr("y", 6)
            .attr("dy", ".75em")
            .attr("transform", "rotate(-90)")
            .text("学生在所属专业的成绩排名（名）");

        // Add the year label; the value is set on transition.
        var label = svg.append("text")
            .attr("class", "year label")
            .attr("text-anchor", "end")
            .attr("y", height - 24)
            .attr("x", width)
            .text(1800);

        // Load the data.
        d3.json("app/pages/sc/template/nations.json", function(nations) {

            // A bisector since many nation's data is sparsely-defined.
            var bisect = d3.bisector(function(d) { return d[0]; });

            // Add a dot per nation. Initialize the data at 1800, and set the colors.
            var dot = svg.append("g")
                .attr("class", "dots")
                .selectAll(".dot")
                .data(interpolateData(1800))
                .enter().append("circle")
                .attr("class", "dot")
                .style("fill", function(d) { return colorScale(color(d)); })
                .call(position)
                .sort(order);

            // Add a title.
            dot.append("title")
                .text(function(d) { return d.name; });

            // Add an overlay for the year label.
            var box = label.node().getBBox();

            var overlay = svg.append("rect")
                .attr("class", "overlay")
                .attr("x", box.x)
                .attr("y", box.y)
                .attr("width", box.width)
                .attr("height", box.height)
                .on("mouseover", enableInteraction);

            // Start a transition that interpolates the data based on year.
            svg.transition()
                .duration(30000)
                .ease("linear")
                .tween("year", tweenYear)
                .each("end", enableInteraction);

            // Positions the dots based on data.
            function position(dot) {
                dot .attr("cx", function(d) { return xScale(x(d)); })
                    .attr("cy", function(d) { return yScale(y(d)); })
                    .attr("r", function(d) { return radiusScale(radius(d)); });
            }

            // Defines a sort order so that the smallest dots are drawn on top.
            function order(a, b) {
                return radius(b) - radius(a);
            }

            // After the transition finishes, you can mouseover to change the year.
            function enableInteraction() {
                var yearScale = d3.scale.linear()
                    .domain([1800, 2014])
                    .range([box.x + 10, box.x + box.width - 10])
                    .clamp(true);

                // Cancel the current transition, if any.
                svg.transition().duration(0);

                overlay
                    .on("mouseover", mouseover)
                    .on("mouseout", mouseout)
                    .on("mousemove", mousemove)
                    .on("touchmove", mousemove);

                function mouseover() {
                    label.classed("active", true);
                }

                function mouseout() {
                    label.classed("active", false);
                }

                function mousemove() {
                    displayYear(yearScale.invert(d3.mouse(this)[0]));
                }
            }

            // Tweens the entire chart by first tweening the year, and then the data.
            // For the interpolated data, the dots and label are redrawn.
            function tweenYear() {
                var year = d3.interpolateNumber(1800, 2014);
                return function(t) { displayYear(year(t)); };
            }

            // Updates the display to show the specified year.
            function displayYear(year) {
                dot.data(interpolateData(year), key).call(position).sort(order);
                label.text(Math.round(year));
            }

            // Interpolates the dataset for the given (fractional) year.
            function interpolateData(year) {
                return nations.map(function(d) {
                    return {
                        name: d.name,
                        region: d.region,
                        income: interpolateValues(d.income, year),
                        population: interpolateValues(d.population, year),
                        lifeExpectancy: interpolateValues(d.lifeExpectancy, year)
                    };
                });
            }

            // Finds (and possibly interpolates) the value for the specified year.
            function interpolateValues(values, year) {
                var i = bisect.left(values, year, 0, values.length - 1),
                    a = values[i];
                if (i > 0) {
                    var b = values[i - 1],
                        t = (year - a[0]) / (b[0] - a[0]);
                    return a[1] * (1 - t) + b[1] * t;
                }
                return a[1];
            }
        });
    }
});