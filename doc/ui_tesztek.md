# UI teszek készítése

A UI tesztekhez az AssertJ Swing JUnit könyvtárat használtuk, amellyel könnyedén tudtunk JUnit alapú teszteket írni az alkalmazásunkhoz. Ezentúl a képernyőn megjelenő komponenseket is lehet kódból manipulálni vele, például a gombokat kattintani, szövegeiket ellenőrizni.
### Az alábbi folyamatokhoz készítettünk teszteket:
* Karakter mozgása
    * Sikeres és sikertelen esetet is leteszteltünk
* Cső elhelyezése
    * Sikeres és sikertelen esetet is leteszteltünk
* Csövön végezhető műveletek
    * Lyukasztás, csúszóssá tétel, ragacsossá tétel
* Fordulók kezelése
    * Karakterek léptetése, fordulók számának növekedése
* A kezdő menü elemei:
    * Körök száma / Játékosok száma
        * Növelés
        * Csökkentés
        * Kézzel bevitt érték
    * Start gomb
        * Játék elindítása
        * Előzőleg bevitt értékek helyes mentése

További problémát okozott, hogy a build pipelineban nem tudtak lefutni a tesztek, mert kellett hozzájuk egy képernyő. Ehhez egy VNC szervert kellett beüzemelnünk, amely egy virtuális képernyőt hoz létre.

### Összefoglaló / tanulság:

Nagyon sok időbe telt minden egyes tesztesetet összerakni, ezért át kell gondolni sok esetben, hogy mikor térül meg automatizált UI teszteket írni a kézi tesztelés helyett, és csak ekkor érdemes használni őket.
