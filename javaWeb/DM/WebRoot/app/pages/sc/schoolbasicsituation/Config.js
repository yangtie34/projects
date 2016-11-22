NS.define('Pages.sc.schoolbasicsituation.Config',{
    singleton: true,
    treeData : function(){
        return {
            "name": "学校",
            color : '#F0F0F0',
            id : 0,
            pid : -1,
            "children": [
                {
                    "name" : "本专科生",
                    color : '#C6E9BF',
                    id : 100,pid : 0,
                    "children" : [
                        {
                            "name" : "学生在籍情况统计","size" : 2000,id : 101,pid : 100,
                            color : '#7CC899',
                            method : 'queryStudentBasic',type : 'column'
                        },
                        {
                            "name" : "分年龄段统计","size" : 2000,id : 102,pid : 100,
                            color : '#73C477',
                            method : 'queryStudentByAgeRange',type : 'column'
                        },
                        {
                            "name" : "按民族统计","size" : 2000,id : 103,pid : 100,
                            color : '#56BB77',
                            method : 'queryStudentByMz',type : 'pie',yAxis: '个',height : 380
                        },
                        {
                            "name" : "按文化程度统计","size" : 2000,id : 104,pid : 100,
                            color : '#8CC986',
                            method : 'queryStudentByRxcc',type : 'column',yAxis: '个'
                        },
                        {
                            "name" : "按学生性别统计","size" : 2000,id : 106,pid : 100,
                            color : '#38AB5D',
                            method : 'queryStudentWithLs',type : 'column',yAxis: '个',width : 500
                        }
                    ]
                },
                {
                    "name" : "教职工",
                    color : '#DBDAEC',
                    id : 200,pid : 0,
                    "children" : [
                        {
                            "name" : "教职工类别统计","size" : 2000,id : 201,pid : 200,
                            color : '#8D85DB',
                            method : 'queryTeacherBasic',type : 'column',
                            /*data : [
                                {name : '本部教师',value : 235,field : null},{name : '外聘教师',value : 45,field : null}
                            ]*/
                        },
                        {
                            "name" : "按性别统计","size" : 2000,id : 202,pid : 200,
                            color : '#928EC0',
                            method : 'queryTeacherBYXb',type : 'column',
                            /*data : [
                                {name : '男',value : 146,field : null},{name : '女',value : 134,field : null}
                            ]*/
                        },
                        {
                            "name" : "按民族统计","size" : 2000,id : 203,pid : 200,
                            color : '#B2A5F5',
                            method : 'queryTeacherByMz' ,type : 'pie',yAxis: '个',height : 380,
                            /*data : [
                                {name : '汉族',value : 272,field : null},{name : '其他民族',value : 8,field : null}
                            ]*/
                        },
                        {
                            "name" : "按年龄段统计","size" : 2000,id : 204,pid : 200,
                            color : '#BDB3E5',
                            method : 'queryTeacherByAgeRange',type : 'pie',yAxis: '人',height : 380,
                            /*data : [
                                {name : '20-30岁',value : 28,field : null},{name : '30-40岁',value : 187,field : null},
                                {name : '40-50岁',value : 49,field : null},{name : '50-60岁',value : 16,field : null}
                            ]*/
                        },
                        {
                            "name" : "按文化程度统计","size" : 2000,id : 205,pid : 200,
                            color : '#756BB0',
                            method : 'queryTeacherByXlcc',type : 'pie',yAxis: '人，占比',
                            /*data : [
                                {name : '研究生',value : 25,field : null},{name : '本科',value : 198,field : null},
                                {name : '其他',value : 57,field : null}
                            ]*/
                        },
                        {
                            "name" : "按技术职称统计","size" : 2000,id : 206,pid : 200,
                            color : '#9E8EBF',
                            method : 'queryTeacherByZc',type : 'column',yAxis: '人',height : 380,
                            /*data : [
                                {name : '正高级',value : 2,field : null},{name : '副高级',value : 59,field : null},
                                {name : '中级职称',value : 155,field : null},{name : '高级工以上',value : 171,field : null}
                            ]*/
                        }
//                        ,
//                        {
//                            "name" : "教职工教学组织结构分布","size" : 2000,id : 207,pid : 200,
//                            color : '#756BB0',
//                            method : 'queryTeacherByYx',type : 'pie',yAxis: '个',height : 380
//                        }
                    ]
                },
                {
                    "name" : "宿舍",
                    color : '#C6DBF0',
                    id : 300,pid : 0,
                    "children" : [
                        {
                            "name" : "学校宿舍资源概况","size" : 2000,id : 301,pid : 300,
                            color : '#3793D4',
                            method : 'queryDormBasic',type : 'text'
                        },
                        {
                            "name" : "学生入住情况统计","size" : 2000,id : 303,pid : 300,
                            color : '#76C0ED',
                            method : 'queryRzqk',type : 'column'
                        }
                    ]
                },
                {
                    "name" : "科研",
                    color : '#FCD0A1',
                    id : 400,pid : 0,
                    "children" : [
                        {
                            "name" : "科研成果(预留接口)","size" : 2000,id : 401,pid : 400,
                            color : '#FF6D24',
                            method : 'queryKcBasic',type : 'text',
                            data : []
                        },
                        {
                            "name" : "科研人员(预留接口)","size" : 2000,id : 402,pid : 400,
                            color : '#FD8C3C',
                            method : 'queryXbkc',type : 'column',width:450,
                            data : []
                        },
                        {
                            "name" : "科研经费(预留接口)","size" : 2000,id : 403,pid : 400,
                            color : '#FF9740',
                            method : 'queryKcfl',type : 'column',
                            data : []
                        }
                    ]
                },
                {
                    "name" : "研究生",
                    color : '#C6E9BF',
                    id : 500,pid : 0,
                    "children" : [
                        {
                            "name" : "基本概况（无数据）","size" : 1000,id : 501,pid : 500,
                            color : '#7CC899',
                            method : 'queryStudentBasic',type : 'column',
                            data:[]
                        },
                        {
                            "name" : "分年龄统计（无数据）","size" : 1000,id : 502,pid : 500,
                            color : '#73C477',
                            method : 'queryStudentByAgeRange',type : 'column',
                            data:[]
                        },
                        {
                            "name" : "按民族统计（无数据）","size" : 1000,id : 503,pid : 500,
                            color : '#56BB77',
                            method : 'queryStudentByMz',type : 'pie',yAxis: '个',height : 380,
                            data:[]
                        },
                        {
                            "name" : "按入学前层次统计（无数据）","size" : 1000,id : 504,pid : 500,
                            color : '#8CC986',
                            method : 'queryStudentByRxcc',type : 'column',yAxis: '个',
                            data:[]
                        }
                    ]
                }
            ]
        };
    }
});