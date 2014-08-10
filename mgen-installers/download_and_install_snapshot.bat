@echo off

echo NOTE: THIS INSTALLER REQUIRES GNU WIN32 TOOLS
echo 

echo Checking MGEN_INSTALL_PATH ...
If "%MGEN_INSTALL_PATH%"=="" (
	echo MGEN_INSTALL_PATH not set, aborting
	Exit /b
)
echo OK: %MGEN_INSTALL_PATH%
echo

echo Downloading MGen-SNAPSHOT
wget http://snapshot.culvertsoft.se/mgen-SNAPSHOT/mgen-SNAPSHOT.zip -O mgen-SNAPSHOT.zip
echo OK
echo

echo Deploying to %MGEN_INSTALL_PATH%
rm -rf temp/
unzip mgen-SNAPSHOT.zip -d temp/
rm -rf %MGEN_INSTALL_PATH%
mkdir %MGEN_INSTALL_PATH%
cp -rf temp/mgen-SNAPSHOT/* %MGEN_INSTALL_PATH%/

mkdir %MGEN_INSTALL_PATH%\jars
for /R temp\ %%f in (*.jar) do copy "%%f" "%MGEN_INSTALL_PATH%\jars\"

mkdir %MGEN_INSTALL_PATH%\bin
cp -rf mgen.ex_ %MGEN_INSTALL_PATH%\bin\mgen.exe
