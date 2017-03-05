# AuctionHunter - Allegro
Aplikacja do automatycznego wyszukiwania przedmiotów w serwisie Allegro (wykorzystująca WebAPI) w paru prostych krokach:  
1. Dodaj nowy cel  
2. Określ dokładne kryteria wyszukiwania  
3. Poczekaj na notyfikację o nowym przedmiocie w serwisie Allegro, który spełnia twoje kryteria  

Allegro WebAPI http://allegro.pl/webapi

Zadaniem aplikacji jest znalezienie przedmiotu z okrelonymi kryteriami takimi jak:
- cena
- kategoria
- oferta Kup Teraz/Licytacja
- dynamiczne filtry dla danej kategorii

Wykorzystywane biblioteki:
- com.squareup.picasso:picasso:2.5.2
- com.alexgilleran:icesoap:1.1.1
- io.realm:realm-gradle-plugin:3.0.0
