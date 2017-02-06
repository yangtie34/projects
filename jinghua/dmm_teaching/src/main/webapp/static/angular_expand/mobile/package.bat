del ng-system.js
del ng-system.min.js
for /f "eol=/" %%i in (dir.txt) do type %%i >> ng-system.js
java -jar yuicompressor-2.4.2.jar --type js --charset utf-8 -o ng-system.min.js ng-system.js