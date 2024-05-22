## Statikus analízis

**SonarCloud**:

A SonarCloudot beüzemeltük a build pipelineba, hogy minden pushnál a main branchen, illetve minden pull requestnél lefusson. Ezután felmértük, milyen problémák/hibák merültek fel a programban. 496 Issue-t talált az első futás után, amiből 46 high severity probléma volt. Nagy részét ezeknek a hibáknak kijavítottuk. Security hotspotokat ellenőriztük a problémákat a random szám generálások és a StackTrace okozta, amelyek viszont a programunkban nem okoztak valós veszélyt.

*A kijavított problémák típusai:*
- Kognitív komplexitás
- Kód formázás
- Refaktorálás
- Erőforrás és hozzáférés kezelésének finomítása
- Security hotspotok átnézése


**Összefoglaló / Tanulság**:

A projektben fejlődött a kód minősége. A feladatban sok apróbbnak vélt probléma kijavítása is a projekt struktúrájának alapvető megváltoztatását, ezáltál viszont számos más helyen is változtatásokat kellett eszközölni a kódban, hogy a program működése azonos maradjon.

