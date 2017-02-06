del Ice.all.js
del Ice.min.js
for /f "eol=/" %%i in (dir.txt) do type %%i >> Ice.all.js
java -jar yuicompressor-2.4.2.jar --type js --charset utf-8 -o Ice.min.js Ice.all.js