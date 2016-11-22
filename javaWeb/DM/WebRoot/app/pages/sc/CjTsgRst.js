/**
 * 图书馆进入平均次数与成绩综合排名，按照男女进行染色
 * User: zhangzg
 * Date: 15-1-5
 * Time: 下午5:19
 *
 */
NS.define('Pages.sc.CjTsgRst',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            querySslRzColumn:'bzksWzsService?getWgWzsColumn'

        }
    },
    tplRequires : [],
    cssRequires : ['app/pages/zksf/css/base.css'],
    mixins:['Pages.sc.Scgj'],
    requires:[],
    params:{},
    init: function () {
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'学生平均排名与平均进出图书馆次数的散列分布',
                pageHelpInfo:'图书馆进入平均次数与成绩综合排名，系统随机从全部在校学生中抽取男生500名，女生500名，并计算他们的平均排名与平均出入图书馆次数。'}
        });
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,containerx]
        });
        this.setPageComponent(container);

        this.fillCompoByData();
    },
    sslChart : new Exp.chart.HighChart({
        height:500
    }),

    /**
     * 创建中部容器组件。
     */
    createMain:function(){
        var containerx = new Ext.container.Container({
            items:[this.sslChart]
        });
        return containerx;
    },
    fillCompoByData:function(){
        this.callService({key:'querySslRzColumn',params:this.params},function(respData){
            $('#'+this.sslChart.getId()).highcharts({
                chart: {
                    type: 'scatter',
                    zoomType: 'xy'
                },
                title: {
                    text: '学生平均排名与平均进出图书馆次数的散列分布'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    title: {
                        enabled: true,
                        text: '平均出入图书馆次数'
                    },
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true
                },
                yAxis: {
                    title: {
                        text: '平均专业成绩排名'
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 100,
                    y: 70,
                    floating: true,
                    backgroundColor: '#FFFFFF',
                    borderWidth: 1
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '平均出入图书馆次数：{point.x} ,<br/> 平均专业成绩排名：{point.y}'
                        }
                    }
                },
                series: [{
                    name: '女生',
                    color: 'rgba(223, 83, 83, .5)',
                    data: [[7, 18], [80, 90], [31, 56], [0, 95], [25, 65], [0, 63], [80, 90], [14, 213], [11, 21], [284, 113], [0, 160], [41, 11], [87, 27], [23, 7], [91, 47], [20, 66], [14, 11], [97, 30], [16, 12], [57, 12], [45, 122], [99, 17], [97, 30], [10, 107], [86, 17], [22, 92], [35, 49], [49, 41], [61, 19], [0, 15], [55, 18], [23, 31], [96, 190], [13, 24], [80, 175], [276, 10], [44, 51], [26, 19], [45, 106], [175, 42], [0, 70], [4, 6], [4, 117], [156, 185], [77, 84], [17, 73], [25, 91], [33, 19], [36, 14], [26, 35], [107, 223], [24, 26], [69, 34], [6, 4], [114, 10], [0, 37], [353, 38], [12, 77], [58, 169], [27, 84], [1, 20], [119, 184], [0, 290], [0, 40], [14, 43], [23, 26], [0, 89], [52, 26], [26, 5], [0, 189], [162, 13], [311, 122], [0, 16], [0, 6], [22, 296], [72, 94], [180, 20], [10, 15], [41, 56],
                        [14, 15], [29, 101], [287, 17], [0, 74], [5, 14], [3, 27], [0, 119], [67, 132], [3, 17], [0, 245], [20, 30], [123, 26], [0, 55], [28, 59], [32, 137], [2, 156], [3, 41], [62, 27], [57, 59], [16, 4], [88, 32], [21, 18], [23, 85], [2, 105], [159, 66], [322, 25], [72, 50], [269, 45], [0, 41], [5, 7], [0, 77], [0, 99], [17, 45], [22, 68], [0, 14], [93, 14], [1, 23], [37, 42], [76, 203], [19, 14], [13, 43], [15, 129], [29, 90], [0, 35], [70, 52], [10, 18], [11, 41], [63, 32], [16, 11], [44, 8], [3, 47], [59, 104], [106, 113], [61, 124], [41, 60], [95, 96], [42, 37], [5, 46], [11, 211], [35, 43], [10, 63], [0, 56], [129, 64], [8, 6], [14, 64], [3, 3], [9, 7], [62, 162], [0, 73], [88, 82], [50, 27], [2, 27], [38, 88], [0, 5], [154, 36], [227, 5], [47, 20], [30, 15], [17, 55], [0, 37], [223, 40],
                        [45, 8], [0, 26], [43, 31], [0, 115], [32, 35], [81, 41], [73, 73], [66, 94], [13, 26], [69, 59], [0, 28], [4, 56], [125, 3], [39, 46], [42, 62], [132, 17], [10, 58], [19, 37], [52, 8], [27, 33], [31, 32], [23, 50], [70, 53], [4, 57], [232, 22], [56, 61], [238, 22], [111, 232], [94, 26], [41, 63], [0, 21], [9, 42], [49, 28], [5, 47], [6, 240], [0, 37], [128, 6], [40, 63], [102, 100], [37, 6], [0, 72], [89, 81], [74, 51], [9, 57], [159, 19], [150, 214], [44, 94], [20, 258], [42, 106], [70, 25], [32, 141], [0, 35], [63, 60], [211, 34], [0, 28], [15, 60], [83, 101], [42, 89], [0, 8], [146, 69], [32, 14], [88, 145], [0, 112], [86, 80], [32, 96], [23, 67], [12, 116], [136, 29], [0, 17], [0, 77], [21, 126], [113, 83], [9, 50], [1, 113], [62, 33], [8, 44], [0, 30], [0, 89], [23, 66], [57, 7],
                        [0, 81], [140, 11], [42, 171], [55, 185], [21, 24], [30, 174], [58, 57], [0, 42], [0, 5], [37, 45], [30, 19], [22, 27], [23, 16], [7, 27], [18, 78], [43, 3], [41, 2], [38, 76], [8, 24], [35, 52], [0, 34], [222, 52], [96, 37], [17, 22], [92, 42], [16, 14], [73, 24], [0, 52], [86, 69], [16, 9], [18, 43], [4, 101], [40, 85], [123, 128], [31, 28], [0, 48], [46, 169], [93, 4], [49, 14], [64, 141], [0, 48], [0, 161], [18, 2], [17, 18], [24, 173], [8, 62], [13, 62], [22, 36], [146, 36], [6, 75], [35, 6], [11, 17], [335, 36], [111, 17], [13, 33], [7, 29], [40, 66], [10, 36], [7, 12], [44, 102], [0, 180], [29, 39], [185, 169], [77, 67], [0, 94], [11, 16], [0, 15], [93, 10], [41, 21], [125, 134], [47, 40], [81, 108], [35, 26], [150, 10], [24, 115], [0, 65], [233, 28], [28, 142], [0, 175], [0, 67],
                        [56, 181], [3, 9], [0, 76], [167, 85], [3, 36], [406, 63], [0, 32], [251, 68], [301, 36], [43, 36], [0, 91], [80, 21], [24, 32], [6, 4], [0, 43], [0, 18], [0, 31], [9, 26], [129, 11], [86, 70], [225, 171], [137, 13], [131, 17], [159, 19], [14, 97], [90, 67], [63, 30], [159, 40], [187, 74], [160, 88], [83, 23], [12, 30], [23, 7], [0, 34], [63, 26], [27, 74], [2, 17], [16, 43], [49, 48], [57, 80], [17, 17], [0, 49], [41, 6], [294, 63], [18, 76], [52, 28], [50, 33], [34, 56], [10, 26], [197, 25], [43, 48], [52, 49], [7, 39], [0, 22], [12, 87], [29, 285], [16, 42], [15, 24], [30, 21], [97, 53], [19, 231], [17, 89], [10, 24], [55, 11], [13, 49], [0, 35], [109, 45], [26, 35], [36, 70], [21, 3], [66, 17], [14, 122], [0, 104], [126, 96], [75, 11], [6, 16], [30, 103], [9, 98], [5, 17], [22, 9],
                        [33, 51], [18, 6], [86, 12], [72, 34], [19, 41], [4, 19], [50, 64], [115, 49], [27, 54], [115, 24], [0, 33], [75, 66], [13, 10], [64, 58], [145, 82], [94, 33], [72, 13], [17, 107], [118, 58], [135, 57], [0, 94], [30, 41], [200, 6], [0, 45], [157, 62], [113, 72], [209, 17], [0, 28], [73, 44], [526, 42], [123, 5], [44, 18], [0, 22], [155, 2], [41, 29], [0, 9], [71, 16], [72, 40], [12, 36], [12, 143], [29, 51], [14, 4], [0, 265], [4, 31], [199, 98], [43, 36], [16, 144], [68, 10], [39, 9], [9, 16], [5, 238], [115, 29], [0, 142], [0, 21], [0, 123], [17, 69], [215, 47], [0, 25], [6, 25], [70, 56], [0, 10], [70, 44], [1, 26], [176, 3], [61, 124], [83, 33], [2, 153], [13, 12], [21, 64], [0, 211], [48, 54], [14, 25], [12, 43], [134, 30], [51, 25], [64, 89], [48, 20], [0, 75], [19, 79], [27, 22],
                        [21, 54], [83, 34], [19, 32], [63, 3], [21, 51], [78, 25], [55, 100], [3, 37], [6, 113], [71, 5], [52, 12], [105, 71], [18, 199], [80, 55], [0, 66], [26, 141], [0, 115], [10, 28], [138, 24], [0, 184], [15, 42], [0, 148], [0, 140], [121, 75], [24, 13], [2, 122], [235, 190], [24, 44], [37, 17], [0, 71], [0, 111], [57, 54], [0, 27], [7, 29], [54, 129], [36, 84], [10, 110], [28, 70], [24, 74], [9, 15], [0, 49], [63, 28], [203, 8], [0, 26], [59, 236], [184, 12], [0, 111], [24, 275], [33, 165], [48, 43], [288, 35], [44, 72], [28, 118], [112, 53], [65, 9], [1, 11], [63, 30], [7, 36], [24, 211], [38, 144], [61, 93], [55, 68], [19, 36], [83, 59], [7, 31], [25, 43], [101, 138], [33, 27], [16, 97], [47, 17], [6, 256], [12, 31], [12, 34], [162, 43], [30, 49], [219, 11], [40, 282], [5, 98], [44, 68],
                        [57, 58], [23, 66], [19, 33], [0, 41], [40, 55], [20, 139], [93, 10], [21, 59], [18, 64], [23, 42], [0, 13], [0, 51], [123, 17], [40, 55], [0, 78], [30, 174], [182, 74], [0, 117], [51, 30], [36, 108], [48, 159], [21, 62], [0, 141], [4, 157], [0, 27], [149, 7], [36, 37], [429, 70], [27, 25], [78, 39], [124, 68], [57, 20], [60, 125], [7, 29], [170, 51], [0, 79], [0, 76], [67, 16], [82, 34], [100, 64], [41, 29], [6, 41], [32, 66], [82, 109], [8, 165], [265, 52], [19, 58], [0, 71], [85, 227], [233, 33], [30, 31], [9, 9], [21, 70], [317, 9], [0, 39], [73, 62], [6, 19], [31, 13], [39, 17], [47, 140], [0, 109], [84, 125], [12, 79], [0, 29], [3, 105], [60, 22], [1, 41], [0, 71], [26, 16], [150, 44], [28, 36], [8, 24], [101, 138], [36, 36], [21, 111], [2, 108], [6, 32], [134, 7], [5, 129], [17, 74],
                        [9, 17], [2, 12], [30, 105], [52, 115], [53, 22], [44, 46], [203, 35], [20, 22], [0, 101], [35, 20], [35, 129], [45, 81], [20, 36], [154, 90], [31, 95], [71, 31], [21, 129], [84, 125], [4, 110], [0, 21], [30, 28], [0, 4], [52, 50], [0, 8], [21, 130], [0, 27], [39, 219], [18, 22], [45, 47], [0, 23], [13, 21], [31, 59], [56, 16], [84, 20], [0, 44], [25, 8], [0, 104], [14, 39], [180, 66], [49, 9], [0, 19], [15, 34], [8, 33], [17, 50], [0, 314], [118, 23], [3, 54], [68, 51], [33, 237], [52, 85], [79, 78], [44, 24], [0, 8], [0, 31], [17, 68], [82, 178], [2, 255], [0, 134], [31, 126], [10, 1], [25, 45], [8, 2], [0, 126], [2, 51], [56, 62], [2, 70], [60, 17], [0, 33], [104, 31], [96, 85], [183, 2], [16, 150], [13, 4], [40, 43], [47, 78], [0, 122], [53, 28], [48, 10], [6, 85], [10, 119], [95, 17],
                        [40, 14], [79, 35], [15, 109], [42, 129], [92, 27], [18, 239], [286, 28], [28, 6], [20, 3], [88, 43], [6, 53], [84, 29], [61, 11], [154, 21], [2, 45], [0, 14], [5, 120], [80, 46], [1, 21], [15, 23], [1, 80], [89, 75], [29, 48], [5, 82], [0, 157], [0, 68], [70, 10], [69, 45], [4, 51], [23, 9], [118, 91], [58, 18], [271, 12], [75, 218], [85, 35], [241, 8], [30, 47], [0, 38], [132, 5], [19, 8], [211, 26], [52, 65], [0, 8], [29, 83], [0, 29], [32, 28], [77, 15], [11, 139], [79, 14], [8, 80], [0, 72], [81, 68], [216, 16], [22, 9], [6, 52], [37, 93], [19, 104], [0, 21], [80, 36], [13, 63], [12, 48], [75, 2], [20, 112], [22, 47], [1, 48], [17, 44], [35, 29], [23, 27], [39, 195], [200, 19], [58, 65], [64, 120], [46, 57], [39, 54], [375, 64], [190, 29], [25, 99], [12, 58], [62, 37], [0, 98], [0, 190],
                        [95, 87], [190, 28], [13, 22], [32, 59], [95, 45], [369, 31], [27, 36], [48, 62], [271, 23], [195, 13], [82, 35], [20, 196], [57, 38], [0, 7], [34, 46], [0, 148], [12, 108], [0, 245], [44, 25], [33, 175], [83, 55], [127, 72], [130, 64], [78, 12], [0, 21], [6, 149], [0, 94], [0, 33], [15, 38], [56, 1], [169, 38], [42, 35], [113, 48], [3, 37], [47, 44], [77, 15], [76, 60], [130, 13], [143, 60], [0, 17], [92, 26], [36, 62], [100, 50], [32, 7], [5, 58], [73, 126], [13, 16], [59, 187], [0, 89], [37, 36], [17, 64], [71, 5], [17, 140], [73, 27], [25, 37], [26, 164], [48, 50], [41, 50], [40, 39], [16, 27], [68, 23], [91, 35], [16, 40], [16, 105], [96, 7], [27, 13], [31, 86], [207, 9], [24, 16], [35, 28], [26, 15], [94, 44], [160, 12], [357, 84], [17, 45], [68, 51], [7, 129], [0, 182], [26, 37], [86, 57],
                        [90, 14], [139, 174], [135, 196], [26, 31], [63, 34], [45, 142], [0, 30], [34, 183], [204, 19], [118, 28], [0, 13], [4, 34], [19, 183], [36, 56], [189, 32], [13, 10], [5, 105], [210, 129], [10, 1], [141, 34], [4, 34], [609, 54], [35, 26], [147, 2], [174, 95], [18, 7], [58, 27], [34, 32], [4, 118], [103, 6], [0, 86], [94, 117], [51, 137], [125, 134], [2, 49], [31, 66], [12, 103], [48, 56], [111, 196], [27, 73], [8, 19], [0, 28], [10, 7], [188, 120], [159, 6], [5, 98], [68, 3], [101, 25], [20, 196], [77, 102], [3, 37], [60, 32], [0, 71], [5, 19], [34, 12], [51, 34], [78, 60], [2, 12], [4, 79], [40, 177], [47, 17], [40, 24], [39, 41], [5, 129], [0, 16], [90, 16], [0, 5], [0, 39], [52, 115], [47, 17], [15, 81], [220, 99], [0, 95], [162, 43], [30, 49], [77, 86], [19, 83], [136, 71], [0, 64], [99, 5],
                        [9, 78], [138, 133], [85, 14], [50, 77], [118, 14], [52, 166], [26, 91], [17, 108], [3, 66], [73, 62], [0, 21], [21, 61], [0, 88], [29, 84], [226, 14], [0, 164], [138, 61], [0, 89], [66, 28], [92, 132], [31, 14], [106, 25], [0, 92], [175, 11], [0, 23], [41, 65], [121, 77], [0, 11], [18, 67], [18, 96], [66, 30], [2, 15], [104, 35], [94, 13], [17, 30], [21, 142], [114, 79], [110, 82], [2, 39], [194, 63], [1, 67], [7, 6], [29, 8], [23, 7], [69, 24], [313, 81], [57, 17], [25, 92], [70, 64], [25, 61], [53, 10], [0, 117], [56, 28], [2, 110], [321, 28], [52, 13], [46, 51], [18, 125], [32, 28], [302, 40], [40, 25], [96, 24], [15, 1], [95, 9], [29, 45], [102, 21], [2, 17], [47, 23], [207, 3], [7, 18], [57, 133], [3, 47], [91, 5], [37, 111], [0, 20], [8, 148], [1, 20], [66, 104], [0, 73], [0, 84], [46, 19],
                        [102, 29], [0, 3], [4, 14], [18, 102], [174, 18], [150, 7], [18, 18], [11, 10], [5, 179], [10, 15], [0, 9], [189, 65], [40, 34], [5, 55], [5, 23], [19, 33], [0, 304], [0, 82], [3, 21], [66, 56], [0, 39], [4, 118], [0, 33], [28, 59], [19, 33], [31, 232], [65, 7], [6, 103], [14, 23], [0, 193], [7, 31], [6, 32], [49, 22], [27, 104], [61, 53], [0, 241], [20, 6], [0, 90], [362, 41], [23, 18], [83, 33], [10, 189], [45, 135], [143, 29], [29, 43], [178, 92], [38, 133], [107, 5], [74, 27], [170, 28], [60, 33], [49, 71], [141, 10], [126, 12], [2, 49], [5, 33], [47, 241], [94, 26], [54, 56], [6, 56], [15, 37], [138, 170], [17, 57], [196, 58], [3, 17], [0, 180], [28, 6], [3, 170], [32, 12], [37, 248], [130, 77], [196, 58], [0, 84], [42, 53], [0, 198], [84, 48], [46, 43], [144, 9], [50, 88], [50, 192], [40, 18],
                        [125, 3], [85, 111], [276, 156], [27, 33], [9, 59], [10, 70], [79, 25], [149, 42], [0, 64], [20, 188], [4, 106], [300, 23], [2, 89], [68, 83], [55, 29], [249, 45], [68, 8], [137, 32], [67, 49], [0, 63], [14, 28], [6, 35], [3, 52], [17, 54], [43, 39], [0, 32], [15, 19], [61, 142], [132, 24], [17, 32], [127, 42], [64, 21], [17, 12], [82, 6], [23, 74], [54, 21], [52, 33], [47, 83], [0, 26], [31, 44], [7, 40], [103, 48], [0, 81], [183, 71], [59, 119], [6, 71], [0, 102], [32, 19], [29, 42], [57, 31], [25, 115], [49, 24], [32, 98], [31, 25], [187, 73], [86, 293], [73, 23], [36, 73], [40, 47], [203, 35], [92, 98], [33, 25], [49, 61], [33, 110], [7, 50], [32, 141], [36, 73], [25, 80], [2, 9], [34, 34], [25, 24], [35, 100], [3, 66], [39, 46], [48, 89], [27, 59], [190, 28], [81, 53], [32, 66], [17, 12], [0, 15],
                        [256, 75]]
                }, {
                    name: '男生',
                    color: 'rgba(119, 152, 191, .5)',
                    data: [[107, 28], [24, 95], [108, 7], [0, 83], [0, 53], [5, 76], [2, 17], [70, 24], [59, 49], [45, 63], [2, 129], [9, 63], [5, 158], [126, 58], [25, 14], [0, 121], [93, 90], [16, 70], [5, 58], [0, 135], [9, 65], [24, 33], [5, 86], [66, 13], [167, 72], [11, 30], [73, 47], [17, 91], [16, 114], [26, 34], [3, 15], [5, 53], [0, 38], [28, 273], [14, 134], [0, 135], [3, 109], [43, 50], [93, 52], [50, 89], [243, 23], [0, 10], [0, 109], [45, 101], [0, 52], [0, 168], [17, 73], [26, 14], [15, 59], [16, 55], [11, 110], [0, 25], [52, 145], [18, 97], [17, 100], [19, 82], [10, 52], [9, 67], [7, 90], [15, 167], [0, 64], [19, 99], [5, 40], [4, 30], [10, 26], [0, 274], [19, 117], [4, 122], [21, 77], [0, 113], [0, 32], [0, 41], [7, 149], [47, 75], [16, 114], [0, 55], [2, 83], [0, 274], [14, 60], [0, 85], [0, 14], [30, 36],
                        [25, 38], [2, 143], [46, 38], [14, 50], [7, 121], [8, 145], [10, 26], [35, 66], [47, 49], [37, 62], [0, 35], [11, 85], [20, 53], [176, 56], [11, 34], [61, 31], [5, 199], [93, 23], [108, 37], [11, 121], [1, 175], [177, 8], [3, 73], [0, 70], [85, 40], [111, 52], [10, 287], [65, 46], [16, 202], [12, 46], [21, 90], [2, 13], [27, 84], [39, 90], [2, 22], [14, 47], [29, 55], [48, 51], [35, 147], [17, 59], [3, 109], [44, 45], [34, 58], [8, 58], [0, 35], [10, 55], [6, 16], [11, 52], [59, 180], [73, 16], [27, 84], [24, 32], [2, 36], [39, 305], [159, 64], [2, 126], [68, 36], [9, 49], [0, 52], [20, 80], [44, 27], [16, 72], [0, 57], [0, 51], [16, 57], [0, 143], [9, 96], [35, 49], [2, 104], [122, 70], [6, 169], [143, 16], [0, 124], [44, 55], [173, 25], [9, 45], [111, 80], [25, 49], [0, 124], [317, 55], [0, 53],
                        [25, 31], [1, 41], [0, 32], [54, 193], [75, 44], [9, 65], [0, 33], [0, 143], [4, 13], [2, 90], [19, 20], [61, 91], [74, 123], [102, 32], [111, 96], [0, 11], [175, 94], [18, 100], [9, 65], [80, 116], [12, 57], [46, 59], [0, 63], [5, 67], [1, 60], [0, 67], [3, 110], [0, 119], [14, 116], [0, 61], [224, 70], [4, 40], [11, 56], [66, 23], [0, 89], [72, 28], [0, 189], [104, 40], [3, 110], [119, 97], [69, 41], [0, 195], [48, 75], [0, 14], [195, 85], [58, 33], [66, 23], [12, 7], [186, 74], [0, 115], [0, 124], [20, 38], [4, 62], [40, 35], [0, 289], [120, 97], [118, 19], [13, 26], [10, 28], [4, 217], [2, 99], [4, 30], [59, 49], [34, 41], [1, 195], [1, 69], [0, 53], [0, 103], [87, 40], [0, 170], [6, 93], [0, 41], [317, 4], [198, 55], [0, 23], [166, 23], [90, 40], [38, 78], [31, 86], [48, 11], [4, 237], [8, 19],
                        [39, 208], [3, 172], [36, 36], [7, 38], [204, 53], [0, 101], [0, 65], [14, 50], [42, 15], [0, 19], [28, 182], [55, 44], [6, 149], [18, 118], [15, 130], [488, 31], [232, 16], [25, 38], [27, 38], [43, 11], [28, 17], [2, 36], [55, 34], [60, 118], [51, 38], [0, 4], [234, 84], [60, 90], [38, 32], [26, 89], [3, 65], [0, 23], [4, 57], [452, 54], [0, 224], [184, 23], [26, 36], [138, 50], [73, 33], [16, 27], [4, 170], [20, 34], [5, 207], [16, 74], [64, 92], [0, 58], [48, 24], [31, 56], [71, 280], [2, 84], [7, 111], [0, 312], [134, 66], [178, 56], [1, 32], [29, 52], [14, 61], [44, 23], [212, 32], [0, 51], [23, 100], [3, 23], [4, 47], [0, 71], [29, 19], [5, 58], [46, 38], [234, 84], [6, 34], [26, 117], [102, 38], [30, 75], [141, 64], [6, 41], [107, 93], [18, 198], [27, 164], [26, 50], [71, 29], [115, 63],
                        [7, 101], [3, 80], [0, 45], [6, 78], [17, 91], [0, 59], [36, 64], [0, 55], [17, 55], [130, 121], [32, 83], [111, 52], [63, 60], [56, 62], [72, 30], [59, 71], [25, 55], [13, 128], [7, 79], [0, 20], [68, 61], [11, 60], [37, 46], [99, 124], [11, 26], [1, 45], [2, 229], [16, 12], [159, 64], [3, 124], [577, 9], [0, 206], [44, 72], [5, 125], [63, 39], [17, 36], [16, 86], [21, 66], [33, 260], [0, 54], [0, 93], [8, 103], [130, 65], [95, 44], [41, 25], [46, 97], [52, 190], [37, 168], [298, 13], [13, 43], [21, 212], [6, 151], [0, 53], [44, 4], [30, 36], [8, 185], [0, 47], [5, 6], [53, 37], [31, 60], [52, 35], [0, 52], [46, 97], [188, 35], [21, 30], [0, 79], [8, 81], [275, 88], [70, 40], [6, 15], [15, 141], [40, 130], [2, 90], [5, 39], [16, 76], [0, 118], [12, 125], [9, 116], [17, 50], [0, 85], [0, 67],
                        [5, 69], [2, 3], [35, 126], [2, 114], [17, 45], [14, 57], [109, 221], [63, 13], [31, 198], [85, 40], [22, 52], [3, 68], [128, 67], [1, 88], [1, 109], [34, 58], [16, 205], [90, 23], [37, 27], [0, 52], [3, 240], [20, 114], [104, 32], [2, 40], [0, 66], [2, 57], [141, 133], [66, 47], [21, 50], [16, 76], [44, 32], [36, 46], [24, 32], [29, 8], [6, 41], [19, 24], [72, 66], [0, 19], [5, 67], [4, 134], [0, 52], [40, 91], [0, 119], [21, 79], [5, 29], [94, 6], [0, 77], [32, 47], [38, 144], [3, 116], [0, 55], [20, 109], [42, 54], [9, 204], [259, 48], [69, 41], [0, 14], [79, 30], [0, 294], [21, 27], [22, 51], [91, 83], [55, 38], [34, 41], [2, 17], [0, 118], [39, 36], [171, 151], [0, 17], [53, 36], [0, 237], [75, 104], [0, 114], [2, 15], [0, 89], [0, 94], [0, 25], [0, 84], [1, 74], [1, 51], [6, 124], [48, 4],
                        [9, 120], [1, 40], [55, 21], [0, 42], [117, 44], [8, 45], [11, 107], [381, 41], [119, 187], [20, 45], [159, 13], [25, 126], [14, 19], [3, 41], [202, 54], [14, 70], [14, 21], [0, 2], [15, 51], [83, 63], [65, 142], [5, 184], [85, 35], [34, 42], [0, 71], [7, 100], [0, 19], [33, 56], [14, 70], [8, 279], [0, 68], [46, 41], [32, 55], [30, 42], [5, 41], [248, 30], [27, 149], [4, 44], [0, 109], [74, 99], [0, 3], [58, 26], [21, 255], [13, 58], [7, 59], [22, 129], [140, 42], [31, 53], [0, 163], [77, 45], [23, 31], [0, 11], [4, 18], [40, 16], [25, 16], [3, 58], [0, 106], [0, 27], [104, 103], [8, 126], [4, 154], [13, 55], [40, 41], [23, 109], [3, 104], [60, 187], [9, 101], [26, 28], [6, 54], [69, 34], [5, 54], [123, 42], [38, 14], [1, 35], [2, 108], [96, 32], [0, 21], [5, 34], [20, 84], [53, 57], [3, 65],
                        [48, 84], [79, 31], [0, 128], [51, 92], [36, 114], [109, 53], [26, 20], [23, 41], [0, 21], [0, 40], [0, 103], [1, 25], [18, 164], [27, 38], [30, 80], [147, 32], [1, 83], [409, 38], [0, 22], [5, 33], [3, 109], [545, 137], [42, 37], [119, 24], [2, 18]]
                }]
            });
        },this);
    }
});