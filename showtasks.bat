call runcrud.bat
if "%ERRORLEVEL%" == "0" goto showTasksMozilla
echo.
echo Errors accured, browser will not be initialized
goto fail

:showTasksMozilla
set url="http://localhost:8080/crud/v1/task/getTasks"
start "" "C:\Program Files\Mozilla Firefox\firefox.exe" %url%
if "%ERRORLEVEL%" == "0" goto :end

:fail
echo.
echo Please check errors above

:end
echo.
echo reached end of file