del cg-custom.js
del cg-custom.min.js
for /f "eol=/" %%i in (dir.txt) do type %%i >> cg-custom.js
java -jar yuicompressor-2.4.2.jar --type js --charset utf-8 -o cg-custom.min.js cg-custom.js