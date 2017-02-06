/**
 * Created by Sunweiguang on 2017-1-10.
 */
//首先引入样式文件
require("./css/angular-expand.css");

//引入开始文件
var system = require("./index.js")();

//services导入
require("./service/echartService.js")(system);
require("./service/toastrService.js")(system);
require("./service/httpService.js")(system);
require("./service/locationService.js")(system);
require("./service/dialog.js")(system);
require("./service/sysCodeService.js")(system);

//directives导入
require("./directives/pagination.js")(system);
require("./directives/cgTree.js")(system);
require("./directives/checkPermiss.js")(system);
require("./directives/cgComboTree.js")(system);
require("./directives/cgMulQueryComm.js")(system);
require("./directives/drag.js")(system);
require("./directives/btnDropdown.js")(system);
require("./directives/modalForm.js")(system);
require("./directives/echart.js")(system);
require("./directives/partModal.js")(system);
require("./directives/tooltip.js")(system);
require("./directives/selfDefinedYear.js")(system);


