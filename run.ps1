$ErrorActionPreference = "Stop"
New-Item -ItemType Directory -Force -Path out, data | Out-Null
$sources = Get-ChildItem -Path src/main/java -Filter *.java -Recurse | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
java -cp out com.mnr.fooddelivery.ui.Main
