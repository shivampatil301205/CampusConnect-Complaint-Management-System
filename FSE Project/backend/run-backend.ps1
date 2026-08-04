$MavenDir = Join-Path $PSScriptRoot "maven"
$MavenBin = Join-Path $MavenDir "apache-maven-3.9.6\bin\mvn.cmd"

if (-not (Test-Path $MavenBin)) {
    Write-Host "Local Maven not found. Downloading Apache Maven..." -ForegroundColor Cyan
    $ZipPath = Join-Path $PSScriptRoot "maven.zip"
    $Url = "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
    
    # Download Maven
    Invoke-WebRequest -Uri $Url -OutFile $ZipPath
    
    Write-Host "Extracting Maven..." -ForegroundColor Cyan
    Expand-Archive -Path $ZipPath -DestinationPath $MavenDir -Force
    
    # Remove downloaded zip
    Remove-Item -Path $ZipPath -Force
    Write-Host "Maven setup complete." -ForegroundColor Green
}

Write-Host "Starting Spring Boot Backend..." -ForegroundColor Green
& $MavenBin spring-boot:run
