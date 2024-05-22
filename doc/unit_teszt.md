# Unit Teszt


Első lépésként megismerkedtünk a JUnit5 és a Mockito keretrendszerrel, majd ezek segítségével valósítottuk meg a teszteseteket.

A tesztelésre többféle megközelítést alkalmaztunk:
- Kis függvényeket és azok működését vagy nagyobb kódrészletek közös működését vizsgáltuk
- A függvények viselkedését és azok hívásának kapcsolatait, másrészt pedig a lefutásuk eredményének állapotát is

## Kis függvények tesztelése

A megközelítés célja az volt, hogy minél szeparáltabban ellenőrizzük a függvények működését. Ehhez a mockito mock módszere lett használva. A Singleton osztályok összefonódása igencsak megnehezítette az elején ezt a teljes szeparációt, de nagy részben sikeres lett a végére a dolog.
Mivel a függvények viszonylag egyszerűek voltak logikájukban, ezért a módszerek jobb megismerése céljából több tesztesetnél inkább pluszként lett felhasználva az adott test feature.

Felhasznált feature-ök:
- Parameterized Test
- Test Factory
- Nested Test
- Egyéb alapvető dolgok 

### Létrehoztunk állapot ellenőrző teszteseteket:
- A repairman helyének változására vonatkozó ellenőrzés, illetve az egyes tulajdonságainak változásának követése
- A különböző pályaelemek tulajdonságaiban bekövetkezett változások ellenőrzése
- Az elemek és a repairman közötti kapcsolatok változásának követése



## Kódrészletek összefüggő tesztelése

A tesztek célja a különböző Element-ek együttműködésének vizsgálata.

Létrehoztunk egy közös ősosztályt minden víz szimulációval foglalkozó teszt inicializálásához, mely elérhetővé teszi a pályaelemeket tesztelésre, valamit a pálya felépítésében és a pontszámítás mellett a víz szimuláció mock-olásában is segítséget nyújt.

A kiinduló pálya egy Ciszternából, 2 pumpából és a egy Forrásból áll és az ezeket összekötő 4 csőből.

### Létrehoztunk állapot ellenőrző teszteseteket:
- A pálya létrehozás utáni állapotának helyességére, így biztosítva hogy minden teszt alapja közös és helyes.
- A víz szimulációra a víz meglétének vizsgálatával, mind lyukas, mind sima cső esetén.
- A ponszámítás helyességére, mind a szabotőrök, mind a szerelők számára.

### Létrehoztunk függvényhívást ellenőrző teszteket:
- A Mockito Spy használatával teszteltük a vízszimuláció esetében a step() függvények meghívódását, ezáltal biztosítva a helyes szimulációt.
