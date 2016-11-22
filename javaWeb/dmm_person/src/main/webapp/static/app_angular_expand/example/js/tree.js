custom.controller('app','controller', ['$scope','TreeData',function ($scope, TreeData) {
  var vm = $scope.vm = {};

  vm.countries = 
    {
      mc: '中国',
      children: [
        {
          mc: '北京',
          children: [
            {
              mc: '朝阳区',
              children:[{
            	mc:"北京-朝阳区-1"  
              },{
            	mc:"北京-朝阳区-2"  
              }]
            },
            {
              mc: '宣武区'
            },
            {
              mc: '海淀区'
            }
          ]
        },
        {
          mc: '河北',
          children: [
            {
              mc: '石家庄'
            },
            {
              mc: '承德'
            },
            {
              mc: '唐山'
            }
          ]
        }
      ]
    };
  vm.tree = new TreeData(vm.countries);
}]);