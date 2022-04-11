# Scanneri projekt Delta tehnikalaenutusele
OOP projekt
Mihkel Tiks, Priidik Meelo Västrik

*Projekti eesmärk on luua programm, mis loeb informatsiooni triipkoodilugejalt ja kasutaja inputist, seob need omavahel ja salvestab lõpuks tekstifaili, kus on kirjas
kõik Delta hoone tehnika seast laenutatud esemed; kes, millal ja kui pikaks ajaks need laenutas. Hetkel programm:
  1. loeb eseme koodi triipkoodilugejalt(mis käitub klaviatuuri sisendina) või klaviatuuri sisendist
  2. loeb isikuinfo ja kontrollib kas antud isik eksisteerib ("nimi, perenimi, isikukood)
  3. kui eksisteerib siis salvestab eseme vastavale isikule laenutatuks, muidu loob uue isiku ja salvestab selle talle
Projekt 2-ks on plaanis vahetada välja 2. sammu manuaalne sisestud lugejaga ülikooli töötajate kiipide jaoks. Teostatud salvestused ja inventar on Google Drive'is
exceli failis, et oleks võimalik teistest ligi pääseda ja mugavalt andmeid muuta. Excelis olev info loetakse käivitusel programmi ja programmi täiendatakase jooksvalt.

Inventar - siia on salvestatud kogu tehnika ja laenutajate kirjeldused(looInventarFailist(), getLaenutajad(), lisaLaenutaja(), getTehnika()).
Laenutaja - kõik laenutajad on seda tüüpi, sisaldab andmeid ja isiku laenutusi(getLaenutused(), getIsikukood()).
Laenutus - sisalab informatsiooni teostatud laenutuse kohta - laenutaja, tehnika, algus- ja lõppajada jm. (getLaenutaja(), getTehnika()). 
Tehnika - kirjeldus igast tehnikaesemest, tema kood ja laenutuste ajalugu.
Peaklass - loob tekstifailist inventari, võtab vastu sisestatud andmed ja salvestab teisi andmeid failidesse, loob laenutajaid, kontrollib erinevate sisestuste tõesust.
Kokkuvõttes vahendab ja teostab kõik soovitud protsessid antud klasside ja andmetega (otsiLaenutajat(String isikukood, Inventar inventar),
teostaLaenutus(Scanner triipkoodiLugeja, Inventar inventar), salvestaObjektFaili(Object o, String failinimi)).

Protsess:
Triipkoodilugeja saabumisel saime teada, et ta lihtsalt tõlgendab triipkoodi vastavaks Stringiks ja selle saab lihtsasti inputiga kätte. Koostasime klassid, 
mida pidasime siinkohal tarvilikuks, ehk põhilised Laenutus, Laenutaja ja Tehnika. Peale seda on kõige mahukam osa olnud info salvestamine ja selle kättesaamine.
Priidik tegeles rohkesti Drivest Excelist lugemise ja kirjutamisega ja selle tööle saamisel sai ka klasse vastavalt kohdatud. Loodud inventar ja peaklassi meetodid.

Projekt valmis valdavalt viimase nädala jooksul suhteliselt võrdse panusega meetodite, klasside ja struktuuri planeerimise osas. Andmete kirjutamine/ lugemine on
ülekaalukalt Priidiku töö tulemus, see ReadMe aga Mihkli oma.

Kõige keerulisem ja aeganõudvam oli väliste libraryte kasutamise ülesseadmine. Mavenist/ Gradlest teadmata otsisime alguses jar faile netist, mis oli väga tülikas. Lõpuks saime aru, kuidas Gradle töötab, seega teise osa teeme gradle projekti.

Programm töötab juba praegu tegelikult kasulikul kujul ja hetkel selle projekti raames soovitud funktsionaalsus on saavutatud, kuid selleks et ta oleks päriselt mugav ja säästaks praeguse süsteemiga võrreldes piisavalt aega oleks veel vaja kasutajaliidest ja kiibiskännerit, mille realiseerime tõenäoliselt teise projekti jooksul.

Proovisime laenutada esemeid, mille olime inventari loonud ja tegime seda uute ning ka eksisteerivate isikutega. Kontrollisime kas programm salvestab sooritud andmed ja kas salvestused on korrektsed.
