var dmm= dmm || {};
dmm.D3Tree=function(json,dom){
		handleData(json);
        var m = [20, 120, 20, 120],
            w = 1280 - m[1] - m[3],
            h = 800 - m[0] - m[2],
            i = 0,
            root;

        var tree = d3.layout.tree().size([h, w]);
        var diagonal = d3.svg.diagonal().projection(function(d) { return [d.y, d.x]; });
        var str='<style type="text/css">.node circle {cursor: pointer;stroke: #0099FF;stroke-width: 1.5px; } path.link {fill: none;stroke: #ccc;stroke-width: 1.5px;}</style>';
        
		$(dom).before(str)
        var vis = this.vis = d3.select(dom).append("svg:svg")
            .attr("width", w + m[1] + m[3])
            .attr("height", h + m[0] + m[2])
            .append("svg:g")
            .attr("transform", "translate(" + (m[3]+10) + "," + m[0] + ")");
            root = json;
            root.x0 = h / 2;
            root.y0 = 0;

            function toggleAll(d) {
                if (d && d.children) {
                    d.children.forEach(toggleAll);
                    toggle(d);
                }
            }
            root.children.forEach(toggleAll);
            toggle(root.children[1]);
            if(root.children[1].children && root.children[1].children[2])
                toggle(root.children[1].children[2]);
            if(root.children[6])
            toggle(root.children[6]);
            if(root.children[6] && root.children[6].children && root.children[6].children[1])
                toggle(root.children[6].children[1]);
            update(root);

        function update(source) {
            var duration = d3.event && d3.event.altKey ? 5000 : 500;

            // Compute the new tree layout.
            var nodes = tree.nodes(root).reverse();

            // Normalize for fixed-depth.
            nodes.forEach(function(d) { d.y = d.depth * 180; });

            // Update the nodes…
            var node = vis.selectAll("g.node")
                .data(nodes, function(d) { return d.id || (d.id = ++i); });

            // Enter any new nodes at the parent's previous position.
            var nodeEnter = node.enter().append("svg:g")
                .attr("class", "node")
                .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
                .on("click", function(d) { toggle(d); update(d); });

            nodeEnter.append("svg:circle")
                .attr("r", 1e-6)
                .style("fill", function(d) { return d.children&& d.children.length>0 ? "lightsteelblue" : "#fff"; });

            nodeEnter.append("svg:text")
                .attr("x", function(d) {
                    return isChildren(d) ? -10 : 10;
                })
                .attr("dy", ".35em")
                .attr("text-anchor", function(d) { return isChildren(d) ? "end" : "start"; })
                .text(function(d) { return d.text; })
                .attr('fill-opacity',1)
                .attr('font-size',getFontSize)
                .attr("font-family",'Microsoft YaHei')
                .attr("fill",function(d){
                    return getTextColor(d);
                })
                .style("fill-opacity", 1e-6);

            // Transition nodes to their new position.
            var nodeUpdate = node.transition()
                .duration(duration)
                .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

            nodeUpdate.select("circle")
                .attr("r", 4.5)
                .style("fill", function(d) { return d._children ? "#CCEBFF" : "#fff"; });

            nodeUpdate.select("text")
                .style("fill-opacity", 1);

            // Transition exiting nodes to the parent's new position.
            var nodeExit = node.exit().transition()
                .duration(duration)
                .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
                .remove();

            nodeExit.select("circle")
                .attr("r", 1e-6);

            nodeExit.select("text")
                .style("fill-opacity", 1e-6);

            // Update the links…
            var link = vis.selectAll("path.link")
                .data(tree.links(nodes), function(d) { return d.target.id; });

            // Enter any new links at the parent's previous position.
            link.enter().insert("svg:path", "g")
                .attr("class", "link")
                .attr("d", function(d) {
                    var o = {x: source.x0, y: source.y0};
                    return diagonal({source: o, target: o});
                })
                .transition()
                .duration(duration)
                .attr("d", diagonal);

            // Transition links to their new position.
            link.transition()
                .duration(duration)
                .attr("d", diagonal);

            // Transition exiting nodes to the parent's new position.
            link.exit().transition()
                .duration(duration)
                .attr("d", function(d) {
                    var o = {x: source.x, y: source.y};
                    return diagonal({source: o, target: o});
                })
                .remove();

            // Stash the old positions for transition.
            nodes.forEach(function(d) {
                d.x0 = d.x;
                d.y0 = d.y;
            });
        }

        function toggle(d) {
            if(d.children && d.children.length == 0)d.children = null;
            if (d.children) {
                d._children = d.children;
                d.children = null;
            } else {
                d.children = d._children;
                d._children = null;
            }
        }
        function isChildren(d){
            if(d.children)   return d.children.length>0;
            if(d._children)  return d._children.length>0;
            return false;
        }
        function getTextColor(d){
            var cclx = d.cclx;
            switch(cclx){
                case  'XX' || 'xx':return '#463CFD';
                case  'YX' || 'yx':return '#A93CFD';
                case  'ZY' || 'zy':return '#FF3CC8';
                case  'BJ' || 'bj':return '#FF7A3C';
                default : return '#A93CFD';
            }
        }
        function getFontSize(d){
            var cclx = d.cclx;
            switch(cclx){
                case  'XX' || 'xx':return 14;
                case  'YX' || 'yx':return 12;
                case  'ZY' || 'zy':return 12;
                case  'BJ' || 'bj':return 12;
                default : return 12;
            }
        }
        function handleData(data){
			data.name=data.name_;
			data.text=data.name_;
			data.cclx=data.level_type;
			delete data.level_type;
			delete data.name_;
			if(data.children && data.children.length>0){
				$.each(data.children,function(i,o){
					handleData(o);
				})
			}else if(data.children && data.children.length==0){
				delete data.children;
			}
		}
        return tree;
    }



function getTestData(){
		var data={
		deptId:'',
		name:'周口师院',
		children:[
			{
				deptId:'1',
				name:'计算机系'
			},{
				deptId:'2',
				name:'商务系',
				children:[{
					deptId:'3',
					name:'商务专业1'
				},{
					deptId:'4',
					name:'商务专业2'
				}]
			}
		]
	};
	return data;
}

function createTree(data){
	var handleData=function(d){
		if(d && d.name_)d.name=d.name_;
		if(d && d.children && d.children.length>0){
			$.each(d.children,function(i,o){
				handleData(o);
			})
		}else if(d && d.children && d.children.length==0){
			delete d.children;
		}
	}
	handleData(data);
	console.log(data);
		var option = {
		    title : {
		        text: '教学组织机构'
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		            name:'树图',
		            type:'tree',
		            orient: 'horizontal',  // vertical horizontal
		            rootLocation: {x: 'left',y: 'center'}, // 根节点位置  {x: 100, y: 'center'}
		            nodePadding: 8,
		            layerPadding: 200,
		            hoverable: false,
		            roam: true,
		            symbolSize: 6,
		            itemStyle: {
		                normal: {
		                    color: '#4883b4',
		                    label: {
		                        show: true,
		                        position: 'right',
		                        formatter: "{b}",
		                        textStyle: {
		                            color: '#000',
		                            fontSize: 10
		                        }
		                    },
		                    lineStyle: {
		                        color: '#48b',
		                        shadowColor: '#000',
		                        shadowBlur: 10,
		                        shadowOffsetX: 3,
		                        shadowOffsetY: 5,
		                        type: 'curve' // 'curve'|'broken'|'solid'|'dotted'|'dashed'
		
		                    }
		                },
		                emphasis: {
		                    color: '#4883b4',
		                    label: {
		                        show: false
		                    },
		                    borderWidth: 0
		                }
		            },	            
		            data: [data]
		        }
		    ]
		};
		var charts = echarts.init(document.getElementById('deptTeachTree'));
		charts.setOption(option);
    	charts.on('click', function(target){
    		console.log(target);
    	});
	
	
                    
}