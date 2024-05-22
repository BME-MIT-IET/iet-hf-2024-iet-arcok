# Build keretrendszer + CI beüzemelése
## Projekt konfigurálása Maven-nel:
- Létrehoztuk a pom.xml fájlt.
- Java jdk verziót beállítottuk 21-re
- gitignore beállítottuk a target és idea fájlokra

## GitHub Actions CI beüzemelése
- Létrehoztunk workflow könyvtárat
- Ebben a mapábban hozzáadtunk workflow fájlt maven.yml

## Build és tesztelés
- A projektet ezek után teszteltük különböző fejlesztői környezetekben:
    - InteliJ
    - Visual Studio Code
    - Eclipse
