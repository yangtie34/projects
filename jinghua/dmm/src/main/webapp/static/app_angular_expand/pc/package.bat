del ng-cg-custom.js
del ng-cg-custom-debug.js
for /f "eol=/" %%i in (dir.txt) do type %%i >> ng-cg-custom-debug.js
java -jar yuicompressor-2.4.2.jar --type js --charset utf-8 -o ng-cg-custom.js ng-cg-custom-debug.js