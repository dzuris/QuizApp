package com.example.quizapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.quizapp.CCategory
import com.example.quizapp.CQuestion
import com.example.quizapp.R
import java.io.ByteArrayOutputStream

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null, 1) {

    private var database: SQLiteDatabase? = null
    private val c = context

    //  List of all Categories
    val allCategories: ArrayList<CCategory>
        get() {
            val list = ArrayList<CCategory>()
            database = readableDatabase

            val c: Cursor = database!!.rawQuery("SELECT * FROM $TABLE_CATEGORIES", null)
            if(c.moveToFirst()){
                do {
                    val categoryItem = CCategory(
                        id = c.getInt(c.getColumnIndex(CATEGORIES_ID)),
                        name = c.getString(c.getColumnIndex(CATEGORIES_NAME)),
                        image = c.getBlob(c.getColumnIndex(CATEGORIES_PICTURE)),
                        icon_image = c.getBlob(c.getColumnIndex(CATEGORIES_ICON_PICTURE))
                    )
                    list.add(categoryItem)
                }while (c.moveToNext())
            }

            c.close()
            return list
        }

    //  List of all Questions
    val allQuestions: ArrayList<CQuestion>
        get() {
            val list = ArrayList<CQuestion>()
            database = readableDatabase

            val c: Cursor = database!!.rawQuery("SELECT * FROM $TABLE_QUESTIONS", null)
            if(c.moveToFirst()){
                do {
                    val questionItem = CQuestion(
                        id = c.getInt(c.getColumnIndex(QUESTIONS_ID)),
                        question = c.getString(c.getColumnIndex(QUESTIONS_QUESTION)),
                        optionA = c.getString(c.getColumnIndex(QUESTIONS_OPTION_A)),
                        optionB = c.getString(c.getColumnIndex(QUESTIONS_OPTION_B)),
                        optionC = c.getString(c.getColumnIndex(QUESTIONS_OPTION_C)),
                        optionCorrect = c.getString(c.getColumnIndex(QUESTIONS_OPTION_CORRECT)),
                        category = getCategoryByID(c.getInt(c.getColumnIndex(CATEGORIES_ID)))
                    )
                    list.add(questionItem)
                }while (c.moveToNext())
            }
            c.close()

            return list
        }

    //  On create database, create two tables - Questions Table and Categories Table
    override fun onCreate(db: SQLiteDatabase) {
        this.database = db

        val sqlCreateQuestionsTable = "CREATE TABLE $TABLE_QUESTIONS ($QUESTIONS_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "$QUESTIONS_QUESTION TEXT NOT NULL, " +
                "$QUESTIONS_OPTION_A TEXT NOT NULL, " +
                "$QUESTIONS_OPTION_B TEXT NOT NULL, " +
                "$QUESTIONS_OPTION_C TEXT NOT NULL, " +
                "$QUESTIONS_OPTION_CORRECT TEXT NOT NULL, " +
                "$CATEGORIES_ID INTEGER);"

        val sqlCreateCategoriesTable = "CREATE TABLE $TABLE_CATEGORIES ($CATEGORIES_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "$CATEGORIES_NAME TEXT NOT NULL UNIQUE, " +
                "$CATEGORIES_PICTURE BLOB, " +
                "$CATEGORIES_ICON_PICTURE BLOB);"

        db.execSQL(sqlCreateQuestionsTable)
        db.execSQL(sqlCreateCategoriesTable)
        fillCategoriesTable()
        fillQuestionsTable()
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUESTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        onCreate(db)
    }

    //  Filling Categories Table
    private fun fillCategoriesTable(){
        addCategory("Iné", imageToByteArray(R.drawable.person_another), imageToByteArray(
            R.drawable.image_none))
        addCategory("Geografia", imageToByteArray(R.drawable.person_geography), imageToByteArray(
            R.drawable.icon_boat))
        addCategory("Šport", imageToByteArray(R.drawable.person_sport), imageToByteArray(
            R.drawable.icon_weight))
        addCategory("História", imageToByteArray(R.drawable.person_history), imageToByteArray(
            R.drawable.icon_pen))
        addCategory("Logické", imageToByteArray(R.drawable.person_logic), imageToByteArray(
            R.drawable.icon_brain))
        addCategory("Zvieratá", imageToByteArray(R.drawable.person_animal), imageToByteArray(
            R.drawable.icon_bee))
    }

    //  Filling Questions Table
    private fun fillQuestionsTable(){
        //region Ine
        addQuestion("V ktorom meste vznikla značka Adidas?", "Petrohrad",
            "Scheinfeld", "Insbruck", "Herzogenaurach", 1)
        addQuestion("Každý štvrtý človek v Európe trpí alergiou.Najčastejším typom je alergia:",
            "na púpavy", "na peľ", "na trávu a slamu", "na prach", 1)
        addQuestion("Formát papiera označovaný ako A0 má plochu:", "0,5m", "1,5m", "2m", "1m", 1)
        addQuestion("V ktorom roku bola založená spoločnosť Google?", "1997", "2003", "2007", "1998", 1)
        addQuestion("Kde sídli spoločnosť Heineken?", "Mníchov", "Salzburg", "Insbruck", "Amsterdam", 1)
        addQuestion("Koľko výťahov má budova Empire State?", "72", "76", "100", "73", 1)
        addQuestion("V ktorom roku bol vynájdený prvý počítač?", "1980", "1974", "1960", "1936", 1)
        addQuestion("V ktorom roku bol vytvorený prvý apple počítač?", "1980", "1974", "1936", "1960", 1)
        addQuestion("Ktorý je najväčší štát Európy mimo Ruska?",
            "Francúzsko", "Nemecko", "Španielsko", "Ukrajina", 1)
        //endregion
        //region Geografia
        addQuestion("S ktorým štátom nesusedí štát Irak?", "Jordánsko", "Turecko",
            "Kuvajt", "Azerbajdžan", 2)
        addQuestion("Mesto Lucknow sa nachádza v štáte:", "Estónsko", "Amerika",
            "Anglicko", "India", 2)
        addQuestion("Na ktorom kontinente sa nachádza štát Burundi?", "Ázia", "Oceánia",
            "Nie je to štát", "Afrika", 2)
        addQuestion("Na ktorom kontinente sa nachádza štát Surinam?", "Severná Amerika", "Afrika",
            "Oceánia", "Južná Amerika", 2)
        addQuestion("Cez ktorý z daných štátov prechádza rovník?",
            "Peru",
            "Čad",
            "Austrália",
            "Konžská demokratická republika",
            2)
        addQuestion("Kde sa nachádza Adenský záliv?",
            "Medzi Ománom a Iránom",
            "Západne od  Libanonu",
            "V Paname",
            "Medzi Jemenom a Somálskom",
            2)
        addQuestion("Ktorý z daných štátov je najväčší:", "Mongolsko", "Čile", "Kolumbia", "Irán", 2)
        addQuestion("Ktorý z daných štátov má najväčšiu populáciu?",
            "Fínsko",
            "Slovensko",
            "Nórsko",
            "Dánsko",
            2)
        addQuestion("S ktorým štátom nesusedí Rakúsko?",
            "Slovensko",
            "Švajčiarsko",
            "Taliansko",
            "Chorvátsko",
            2)
        addQuestion("S ktorým štátom susedí Mexiko na svojej južnej hranici?",
            "Kostarika",
            "Kolumbia",
            "Honduras",
            "Guatemala",
            2)
        addQuestion("Kde na mape by sme mohli nájsť štát Nikaragua?",
            "Taký štát neexistuje",
            "V severnej Afrike",
            "Vo východnej Ázii",
            "V strednej Amerike",
            2)
        addQuestion("Ktorý z daných štátov má hranice s viac ako jedným štátom? (vodná hladina sa za hranicu nepokladá)",
            "Gambia",
            "Lesotho",
            "San Maríno",
            "Swaziland",
            2)
        addQuestion("Astana je hlavným mestom:",
            "Kyrgyzstanu",
            "Azerbajdžanu",
            "Ukrajiny",
            "Kazachstanu",
            2)
        addQuestion("Aký štát nenájdeme v Európe?", "Litva", "Macedónsko", "Moldavsko", "Georgia", 2)
        addQuestion("Montevideo je hlavným mestom štátu:",
            "Argentína",
            "Paraguaj",
            "Bolívia",
            "Uruguaj",
            2)
        addQuestion("Aké je hlavné mesto Saudskej Arábie?", "Džidda", "Mekka", "Medina", "Rijád", 2)
        addQuestion("Aké je hlavné mesto Austrálie?", "Sydney", "Melbourne", "Brisbane", "Canberra", 2)
        addQuestion("Aké je hlavné mesto Brazílie?",
            "Sao Paulo",
            "Rio De Janeiro",
            "Fortaleza",
            "Brazília",
            2)
        addQuestion("Aké je hlavné mesto Turecka?", "Istanbul", "Antalya", "Konya", "Ankara", 2)
        addQuestion("Aké je hlavné mesto Kanady?", "Toronto", "Vancouver", "Montreal", "Ottawa", 2)
        addQuestion("Aké je hlavné mesto Holandska?", "Rotterdam", "Utrecht", "Holandsk", "Amsterdam", 2)
        addQuestion("Aké je hlavné mesto Ukrajiny?", "Donetsk", "Charkov", "Moskva", "Kiev", 2)
        addQuestion("Aké je hlavné mesto Spojených Štátov Amerických?",
            "New York",
            "Los Angeles",
            "Boston",
            "Washington DC",
            2)
        addQuestion("Aké je hlavné mesto Nigérie?", "Kano", "Lagos", "Jamaica", "Abuja", 2)
        addQuestion("Aké je hlavné mesto Singapuru?", "Tengah", "Punggol", "Changi", "Singapur", 2)
        addQuestion("Aké je hlavné mesto Španielska?", "Barcelona", "Sevilla", "Valencia", "Madrid", 2)
        addQuestion("Aké je hlavné mesto Indie?", "Bombaj", "Kalkata", "Hajdarabád", "Naí Dillí", 2)
        addQuestion("Aké je hlavné mesto Škótska?", "Wales", "Glasgow", "Aberdeen", "Edinburgh", 2)
        addQuestion("Aké je hlavné mesto Walesu?", "Swansea", "Newport", "Dublin", "Cardiff", 2)
        addQuestion("Aké je hlavné mesto Spojených Arabských Emirátov?",
            "Šarjah",
            "Al Ain",
            "Dubai",
            "Abu Dabí",
            2)
        addQuestion("Aké je hlavné mesto Maroka?", "Marakeš", "Casablanca", "Agadir", "Rabat", 2)
        addQuestion("Aké je hlavné mesto Talianska?", "Miláno", "Benátky", "Neapol", "Rím", 2)
        addQuestion("Aké je hlavné mesto Nemecka?", "Mníchov", "Frankfurt", "Hamburg", "Berlín", 2)
        addQuestion("Riga je hlavné mesto akého štátu?", "Estónsko", "Litva", "Bulharsko", "Lotyšsko", 2)
        addQuestion("Aká rieka preteká cez Paríž?", "Rýn", "Loira", "Dunaj", "Seina", 2)
        addQuestion("Aké je hlavné mesto Pakistánu?", "Láhaur", "Faisalabad", "Karáči", "Islamabád", 2)
        addQuestion("Ktorá krajina má namiesto hlavného mesta federálne?",
            "Slovinsko",
            "Bulharsko",
            "Rakúsko",
            "Švajčiarsko",
            2)
        addQuestion("Ktoré hlavné mesto je najľudnatejšie na svete?",
            "Naí Dillí",
            "Tokyo",
            "Jakarta",
            "Bejing",
            2)
        addQuestion("Aké je hlavné mesto Nového Zélandu?",
            "Auckland",
            "Queenstown",
            "Christchurch",
            "Wellington",
            2)
        addQuestion("Ktoré hlavné mesto je najväčšie na svete?",
            "Mexico City",
            "Londýn",
            "Soul",
            "Tokyo",
            2)
        addQuestion("Ktoré hlavné mesto sa nachádza najjužnejšie?",
            "Montevideo",
            "Buenos Aires",
            "Canberra",
            "Wellington",
            2)
        addQuestion("V ktorom hlavnom meste sa nachádzajú Petronas Twin Towers, najvyššie dvojičkové mrakodrapy na svete?",
            "Singapur",
            "Jakarta",
            "Soul",
            "Kuala Lumpur",
            2)
        addQuestion("Aké je hlavné mesto Kene?", "Mombasa", "Kisumu", "Malindi", "Nairobi", 2)
        addQuestion("Caracas je hlavné mesto akého štátu?",
            "Kolumbia",
            "Paraguaj",
            "Argentína",
            "Venezuela",
            2)
        addQuestion("Aké je hlavné mesto Myanmaru?", "Rangún", "Mandalay", "Mawlamyine", "Nay Pyi Taw", 2)
        //endregion
        //region Sport
        addQuestion("Ktorý z daných herných štýlov nepatrí medzi varianty paintballu?",
            "Obrana a útok",
            "Getysburg",
            "kopnúť do kyblíka",
            "Lov na sliepku",
            3)
        addQuestion("Akú dĺžku má oficiálna bowlingová dráha?", "22m", "16m", "15,5m", "18,5m", 3)
        addQuestion("V ktorých rokoch bol golf na programe Olympijkých hier?",
            "1893 a 1901",
            "1936 a 1940",
            "1966 a 1970",
            "1900 a 1904",
            3)
        addQuestion("Odkiaľ pochádzajú prvé záznamy o bojovom umení?",
            "Z východnej Číny",
            "Zo starovekého Ríma",
            "Z thajska",
            "Zo starovekého Grécka",
            3)
        addQuestion("Ktorá z disciplín nepatrí medzi technické disciplíny cyklistiky?",
            "Sálová cyklistika",
            "cyklotrial",
            "BMX Freestyle",
            "BMX",
            3)
        addQuestion("V ktorom meste sa gymnastika po prvýkrát predstavila na pôde Olympíjskych hier?",
            "Berlín",
            "Rím",
            "Peking",
            "Los Angeles",
            3)
        addQuestion("Prvé záznamy o  zjazde pochádzajú z oblasti Telemarken, kde sa táto oblasť  nachádza?",
            "Rakúsko",
            "Švajčiarsko",
            "Slovensko",
            "Nórsko",
            3)
        addQuestion("Ktorá z disciplín nepatrí medzi moderný päťboj?",
            "Plávanie",
            "Jazda na koni",
            "Streľba pištoľou",
            "Cyklistika",
            3)
        addQuestion("Mix štafetové družstvá v plávaní sa skladajú:",
            "Z troch mužov a troch žien",
            "Zo štyroch žien a štyroch mužov",
            "Z dvoch mužov a dvoch detí",
            "Z dvoch mužov a dvoch žien",
            3)
        addQuestion("Predchodcom snowboardu bol:", "Snowskate", "Skejtbord", "Swingbord", "Snurfer", 3)
        addQuestion("Medzi Brokové disciplíny (streľba) nepatrí:",
            "Trap",
            "Skeet",
            "Americký trap",
            "Kech",
            3)
        addQuestion("Aké je poradie disciplín v triatlone?",
            "Beh - Plávanie - Cyklistika",
            "Cyklistika - Beh - Plávanie",
            "Plávanie - Beh - Cyklistika",
            "Plávanie - Cyklistika - Beh",
            3)
        addQuestion("Loď veslára sa nazýva:", "Kanoe", "Kajak", "Nemá špeciálne meno", "Skif", 3)
        addQuestion("Windsurfing je šport:", "Letecký", "Atletický", "Nie je to šport", "Vodný", 3)
        addQuestion("Akú dĺžku má jedna štvrtina v Americkom futbale?",
            "12min",
            "10min",
            "20min",
            "15min",
            3)
        addQuestion("V ktorej krajine sa konala letná olympiáda v roku 2012?",
            "Francúzsko",
            "Spojené Štáty Americké",
            "Brazília",
            "Anglicko",
            3)
        addQuestion("Koľko bodov treba na vyhratie setu vo volejbale?", "19", "21", "30", "25", 3)
        addQuestion("Aké číslo nosil na drese Michael Jordan?", "18", "14", "6", "23", 3)
        addQuestion("Aké číslo nosí na drese Lionel Messi?", "7", "27", "99", "10", 3)
        addQuestion("Cristiano Ronaldo je hráčom akej krajiny?",
            "Uruguaj",
            "španielsko",
            "Nemecko",
            "Portugalsko",
            3)
        addQuestion("Koľko trvá vo futbale 1 polčas?", "45mm", "45dni", "45hod", "45min", 3)
        addQuestion("Aká je dlhá štvrtina v basketbale?", "12hod", "12dni", "12sec", "12min", 3)
        addQuestion("Koľko hráčov je pri hre na ľade v ľadovom hokeji?", "5", "6", "10", "12", 3)
        addQuestion("Ktorá krajina priniesla volejbal?",
            "Anglicko",
            "Španielsko",
            "Brazília",
            "Spojené Štáty Americké",
            3)
        addQuestion("Aké sú farby olympijských kruhov?",
            "Červená, Žltá, Zelená, Ružová a Čierna",
            "Červená, Žltá, Oranžová, Zelená a Modrá",
            "Červená, Žltá, Zelená, Biela a Čierna",
            "Červená, Žltá, Zelená, Modrá a Čierna",
            3)
        addQuestion("Ktorá krajina vyhrala v histórii najviac medailí na Zimných olympijských hrách? (263)",
            "Švédsko",
            "Rusko",
            "Spojené Štáty Americké",
            "Nórsko",
            3)
        addQuestion("Aký dlhý je olympijský bazén?",
            "70m",
            "30m",
            "25m",
            "50m",
            3)
        addQuestion("Kôš z trestného bodu sa v basketbale počíta za:",
            "1 bod pokiaľ hráč trafí oba trestné hody",
            "3 body",
            "2 body",
            "1 bod",
            3)
        addQuestion("Kedy boli kodifikované oficiálne pravidlá florbalu?",
            "2008",
            "2003",
            "1993",
            "1983",
            3)
        addQuestion("Aký je minimálny počet hráčov v jednom tíme pri futbale?",
            "11",
            "6",
            "8",
            "7",
            3)
        addQuestion("Koľko minút trvá zápas hádzanej pre 16 a viac ročných?",
            "2*15min",
            "3*20min",
            "3*15min",
            "2*30min",
            3)
        addQuestion("Softbal je podobný:",
            "Americkému futbalu",
            "Snookeru",
            "Squashu",
            "Bejzbalu",
            3)
        addQuestion("Z čoho je vyrobená loptička na pingpongu?",
            "Z tvrdeného papiera",
            "Z ľahkej sádry",
            "Z tvrdenej peny",
            "z celuloidu",
            3)
        addQuestion("Z koľkých členov sa skladá mužstvo na vodnom póle?",
            "6",
            "5",
            "8",
            "7",
            3)
        addQuestion("Kde sa organizovali prvé majstrovstvá Európy v hokejbale?",
            "v Paríži",
            "v  Mníchove",
            "v Štokholme",
            "v Bratislave",
            3)
        addQuestion("Bridž je šport:",
            "Letecký",
            "Vodný",
            "Doskový",
            "Kartový",
            3)
        addQuestion("Strategická dosková hra Go pochádza:",
            "Z Thajska",
            "Zo starovekého Grécka",
            "Z Japonska",
            "zo starej Číny",
            3)
        addQuestion("Ktorý z daných športov je najmladší?",
            "Beh",
            "Lukostrelba",
            "Bojové umenie",
            "Skok do diaľky",
            3)
        //endregion
        //region Historia
        addQuestion("Kedy sa začala prvá svetová vojna?", "1918", "1915", "1939", "1914", 4)
        addQuestion("Kedy bol objavený inzulín?", "1925", "1931", "1955", "1921", 4)
        addQuestion("Kedy vznikla Česko-slovenská republika?",
            "17.júla 1918",
            "28.decembra 1917",
            "28.decembra 1918",
            "28.októbra 1918",
            4)
        addQuestion("Kedy sa skončila 2.sv. vojna v Európe?",
            "1. júla 1948",
            "2. septembra 1945",
            "27. augusta 1945",
            "8. mája 1945",
            4)
        addQuestion("KGB bola:",
            "Politická strana v komunizme",
            "Československá štátna polícia",
            "Teroristická skupina",
            "Sovietska štátna polícia",
            4)
        addQuestion("Kto bol americkým prezidentom ku koncu druhej svetovej vojny?",
            "Donald Trump",
            "Ronald Regan",
            "Herbert Hoover",
            "Harry S. Truman",
            4)
        addQuestion("Kto zabil Abrahama Lincolna?",
            "Al Capone",
            "Lee Harvey Oswald",
            "Henry Ford",
            "John Wikes Booth",
            4)
        addQuestion("Komu sa pripisuje výrok, \"Daj mi slobodu alebo mi daj smrť!\"? (Give me liberty or give me death!)",
            "George Washington",
            "Ben Franklin",
            "Bill Gates",
            "Patrick Henry",
            4)
        addQuestion("Kto bol 7.prezidentom Ameriky?",
            "Teddy Roosvelt",
            "Donald Trump",
            "Stewie Bruce",
            "Andrew Jackson",
            4)
        addQuestion("V akom roku objavil Martin Heinrich Klaproth urán?",
            "2016",
            "1756",
            "1911",
            "1789",
            4)
        addQuestion("Kto napadol Pearl Harbor?", "Rusko", "Nemecko", "Čína", "Japonsko", 4)
        addQuestion("Kedy Amerika dosiahla svoju nezávislosť?", "2000", "1784", "1898", "1776", 4)
        addQuestion("Kedy zomrel Fidel Castro?", "1988", "2000", "1950", "2016", 4)
        addQuestion("V ktorom roku dosiahla India nezávislosť od Británie?",
            "2000",
            "1948",
            "1900",
            "1947",
            4)
        addQuestion("FARC je skratka pre partizánske hnutie, kde?",
            "Mexiko",
            "Východná Ázia",
            "Južná Afrika",
            "Kolumbia",
            4)
        addQuestion("Čo bolo rímske meno bohyne Hecate?", "Hectate", "Riddles", "Jesus", "Trivia", 4)
        addQuestion("Kto bol prvým tudorským monarchom v Anglicku?",
            "Henry III.",
            "Jeffrey IIV.",
            "John Milios VI.",
            "Henry VII.",
            4)
        addQuestion("Španielska občianska vojna začala v akom roku?",
            "1940",
            "1930",
            "1945",
            "1936",
            4)
        addQuestion("V ktorom roku bol Abraham Lincoln zavraždený?",
            "1840",
            "1872",
            "1812",
            "1865",
            4)
        addQuestion("V ktorom roku bol založený štát Izrael?",
            "1946",
            "1947",
            "1950",
            "1948",
            4)
        addQuestion("Koncom deväťdesiatych rokov 19. storočia spoločnosť Bayer predávala lieky proti kašľu a bolesti, ktoré obsahujú to, čo je teraz nelegálna droga:",
            "Tráva",
            "Kokaín",
            "Marihuana",
            "Heroín",
            4)
        addQuestion("Kto bol prvým astronautom ktorý navštívil vesmír dvakrát?",
            "Alan Shepard",
            "Neil Amstrong",
            "Yuri Gagarin",
            "Gus Grissom",
            4)
        addQuestion("Značka \"Porsche\" vznikla v akej krajine?",
            "Rakúsko",
            "Bahamy",
            "Taliansko",
            "Nemecko",
            4)
        addQuestion("V ktorom roku bol Nelson Mandela prepustený z väzenia?",
            "1890",
            "2000",
            "1995",
            "1990",
            4)
        addQuestion("Kto bol prvým prezidentom USA, ktorý vyhlásil vojnu?",
            "John Adams",
            "George Washington",
            "Thomas Jefferson",
            "James Madison",
            4)
        //endregion
        //region Logicke
        addQuestion("Pozerám sa na niekoho fotografiu. Uhádnite, kto je na nej odfotený, ak nemám žiadnych súrodencov a otec toho muža na fotografii je syn môjho otca.",
            "Môj otec",
            "Ja",
            "Synovec",
            "Môj syn",
            5)
        addQuestion("\"Ak deň po zajtrajšku je včerajšok, potom 'dnes' bude tak vzdialený od nedele ako deň, ktorý bol 'dnes', keď deň pred včerajškom bolo zajtra!\"\n" + "Ktorý deň v týždni je tento výrok pravdivý? ",
            "Sobota",
            "Streda",
            "Pondelok",
            "Nedeľa",
            5)
        addQuestion("V oddieli 100 vojakov sa po vojne vyskytli nasledovné zranenia: 70 vojakov stratilo oko, 75 stratilo ucho, 85 prišlo o nohu a 80 prišlo o ruku. \n" + "Aký je minimálny počet vojakov, ktorí museli stratiť všetky štyri časti tela? ",
            "15",
            "31",
            "8",
            "10",
            5)
        addQuestion("Práčka ktorá stojí 80€ zdražie o 20%, aká je výsledná suma o ktorú práčka zdražela?",
            "96€",
            "58€",
            "32€",
            "16€",
            5)
        addQuestion("Akým číslom musíme vynásobiť hodnotu 3,6 aby sme dostali výsledok rovný 16,2?",
            "5,3",
            "4,6",
            "5,1",
            "4,5",
            5)
        addQuestion("11,7-9,12=",
            "7,95",
            "1,05",
            "2,05",
            "2,58",
            5)
        addQuestion("Fero mal 35€. Kúpil si čiapku za 17€ a vodu za 1,49€. Koľko peňazí mu ostalo?",
            "18€",
            "16,49€",
            "14,49€",
            "16,51€",
            5)
        addQuestion("17,484/5,64=?",
            "3,001",
            "11,846",
            "5,124",
            "3,1",
            5)
        addQuestion("Maryin otec má 5 dcér. Volajú sa NANA,NENE,NINI,NONO ako sa bude volať 5.dcéra?", "Nunu", "Ninny",
            "Nuna",
            "Mary",
            5)
        //endregion
        //region Zvierata
        addQuestion("Aký je najrýchlejší cicavec na dlhú vzdialenosť?", "Gepard štíhly",
            "Orol sťahovavý", "Človek", "Vidloroh prériový", 6)
        addQuestion("Ktorý cicavec má najväčší rozhľad?", "Puma americká",
            "Slon africký", "Jeleň milu", "Žirafa škvrnitá", 6)
        addQuestion("Ktorý cicavec je najvyšší?", "Slon Africký", "Uškatec medviedi", "Žirafa núbijská", "Žirafa škvrnitá", 6)
        addQuestion("Ktorý cicavej je najmenší", "Lasica myšožravá", "Potkan tmavý", "Rýpoš lysý", "Netopierčík najmenší", 6)
        addQuestion("Ktorý cicavec je najlepší pijan krvi?", "Raniak hrdzavý", "Netopier hnedastý", "Kaloň novoguinejský", "Upíri netopier", 6)
        addQuestion("Ktorý cicavec má najlepší čuch?", "Pes domáci", "Potkan tmavý", "Medveď baribal", "Medveď biely", 6)
        addQuestion("Ktorý cicavec je najrýchlejší na krátku vzdialenosť?", "Sokol sťahovavý", "Puma", "Mačka európska", "Gepard", 6)
        addQuestion("Aký je najväčší suchozemský cicavec?", "Žirafa škvrnitá", "Vorvaň obrovský", "Slon ázijský", "Slon africký", 6)
        addQuestion("Ktorý cicavec žijúci prevažne na strome je najväčší?", "Gorila", "Gibon bieloruský", "Samec mandrila", "Orangutan", 6)
        addQuestion("Ktorý cicavec je najnebezpečnejší?", "Tiger bengálsky", "Kosatka dravá", "Medveď biely", "Potkan tmavý", 6)
        addQuestion("Ktorý cicavec je najväčší?", "Slon africký", "Slon ázijský", "Vráskavec dlhoplutvý", "Vráskavec obrovský", 6)
        addQuestion("Ktorý cicavec je najdlhšie žijúci?", "Slon africký", "Gorila", "Jeleň milu", "Veľryba grónska", 6)
        addQuestion("Ktorý cicavec je najpomalší?", "Mravčiar veľký", "Pásavec plášťový", "Tuleň mechúrnatý", "Leňoch trojprstý", 6)
        addQuestion("Lamatín floridský žije: ", "V lesoch", "V močiaroch", "V piesku", "Vo vode", 6)
        addQuestion("Ktoré cicavce sú známe ako snežné opice?", "Kahauy nosaté", "Ksukoly", "Šimpanzy cheetah", "Makaky japonské", 6)
        addQuestion("Ktoré cicavce sú najhlučnejšie?", "Veľryba južná", "Tapír malajský", "Vrešťan pláštikový", "Vráskavec obrovský", 6)
        addQuestion("Ktorý vták je najväčší?", "Pelikán okuliarnatý", "Juhoamerický hoacin chlochlatý",
            "Americký orol bielohlavý", "Pštros dvojprstý", 6)
        addQuestion("Ktorý vták je najmenší?", "Kolibrík annin", "Kondor golierikatý", "Družník vrabcovitý", "Medovec menší", 6)
        addQuestion("Ktorý vták má najväčšie rozpätie krídeľ?", "Kondor golierikatý", "Americký orol bielohlavý",
            "Pletiarka červenozobá", "Albatros sťahovavý", 6)
        addQuestion("Ktorý vták má najväčší zobák?", "Rybár dlhochvostý", "Papagáj kakadu", "Papagáj ara", "Pelikán okuliarnatý", 6)
        addQuestion("Ktorý vták dokáže najrýchlejšie kmitať krídlami?", "Sluka lesná", "Plamienka driemavá", "Čavka žltozobá", "Kolibrík", 6)
        addQuestion("Ktorý vták dokáže letieť najrýchlejšie", "Americký orol bielohlavý",
            "Družník vrabcovitý", "Albatros sťahovavý", "Sokol sťahovavý", 6)
        addQuestion("Ktorý vták dokáže preletieť najväčšiu vzialenosť bez odpočinku?", "Sokol sťahovavý",
            "Sluka lesná", "Sup biely", "Rybár dlhochvostý", 6)
        addQuestion("Ktorý vták má najlepší zrak?", "Pelikán okuliarnatý", "Albatros sťahovavý", "Kondor golierkatý", "Sluka lesná", 6)
        addQuestion("Ktorý vták má najlepší sluch?", "Tučniak belotemenný", "Labuť spevavá", "Drozd plavý", "Plamienka driemavá", 6)
        addQuestion("Ktorý vták je najrýchlejším plavcom?", "Labuť spevavá", "Albatros čiernobrvý", "Tučniak veľký", "Tučniak belotemenný", 6)
        addQuestion("Ktorý vták je najrýchlejším bežcom", "Plamienka driemavá", "Plameniak červený", "Tučniak cisárský", "Pštros dvojprstý", 6)
        addQuestion("Ktorý vták je najutáranejší?", "Papagáj ara", "Papagáj kakadu", "Juhoamerický hoacin chochlatý", "Papagájec vlnkovaný", 6)
        addQuestion("Ktorý plaz je najväčší?", "Krokodíl dlhohlavý", "Krokodíl nílsky", "Kožatka morská", "Krokodíl morský", 6)
        addQuestion("Ktorý plaz je najhlučnejší?", "Pytón assala", "Kajmanka supia", "Korytnačka slonia", "Aligátor severoamerický", 6)
        addQuestion("Ktorý plaz je najrýchlejší?", "Ropušník", "Pytón mriežkavý", "Gekón lietavý", "Leguán", 6)
        addQuestion("Ktorý plaz má najlepší zrak?", "Tajpan drobnošupinatý", "Aligátor severoamerický", "Kareta veľkohlavá", "Chameleón", 6)
        addQuestion("Ktorý plaz je najdlhší?", "Kobra kráľovská", "Mamba čierna", "Vretenica gabonská", "Pytón mriežkavý", 6)
        addQuestion("Ktorý plaz je najjedovatejší na zemi?", "Kôrnatec gila", "Kobra kráľovská", "Pytón assala", "Tajpan drobnošupinatý", 6)
        addQuestion("Ktorý plaz patrí k najjedovatejším v mori?", "Pytón mriežkavý", "Vretenica rodu Echis", "Gaviál indický", "Mornár belcherov", 6)
        addQuestion("Ktorý plaz je nejnebezpečnejší?", "Mamba čierna", "Aligator severoamerický", "Varan komodský", "Vretenica rodu Echis", 6)
        addQuestion("Ktorý plaz žije najdlhšie?", "Varan komodský", "Kožatka morská", "Korytnačka slonia", "Korytnačka obrovská", 6)
        addQuestion("Ktorý obojživelník je najmenší?", "Veľmlok čínsky", "Taricha kalifornská", "Axolotl", "Kubánska žaba", 6)
        addQuestion("Ktorý obojživelník je najjedovatejší?", "Ropucha obrovská", "Axolotl", "Salamandra waltova", "Stromárka zlatožltá", 6)
        addQuestion("Ktoý obojživelník znesie najviac vajec?", "Stromárka zlatožltá", "Ropucha bradavičnatá", "Veľmlok čínsky", "Ropucha obrovská", 6)
        addQuestion("Ktorý obojživelník má najdlhší skok?", "Africký skokan", "Pseudis podivný", "Ropucha obrovská", "Skokan ostronosý", 6)
        addQuestion("Čím sa vyznačujú Sklovité žaby?", "Veľmi nízkou aktivitou", "Maličkou veľkosťou", "Malými nohami", "Priehľadnosťou", 6)
        addQuestion("Veľmlok čínsky je: ", "Najdlhší obojživelník", "Najrýchlejšia ryba", "Piesočnatý cicavec", "Najväčší obojživelník", 6)
        addQuestion("Najdlhšie žijúcim obojživelníkom je: ", "Axolotl mexický", "Skokan ostronosý", "Salamandra waltova", "Veľmlok japonský", 6)
        addQuestion("Axolotl mexický (Obojživelník) je: ", "Najmenší obojživelník", "Najťažší obojživelník", "Najfarebnejší obojživelník", "Najmenej vyvinutý obojživelník", 6)
        addQuestion("Najťažším obojživelníkom je: ", "Veľmlok čínsky", "Ropucha obrovská", "Salamandra waltova", "Salamandrella keyserlingii", 6)
        addQuestion("Žralok veľrybí je: " , "Najmenším žralokom", "Najdravejšiou rybou", "Nejnebezpečnejšiou rybou", "najväčšiou rybou", 6)
        addQuestion("Najväčšiou sladkovodnou rybou je: ", "Žralok grónsky", "Latiméria divná", "Žralok veľrybí", "Pangasius veľký", 6)
        addQuestion("Ktorá ryba má najrozmanitejšiu potravu?", "Pangasius veľký", "Žralok modrý", "Bahenná skákavá ryba", "Žralok tygrí", 6)
        addQuestion("Najrýchlejšiou rybou je: ", "Priaja rodu Serrasalmus", "Paedocypris progenetica", "Latiméria divná", "Plachetník Istiophorus platypterus", 6)
        addQuestion("Ktorá ryba je známa tým že bola stratená 70 až 80 miliónov rokov a potom sa objavila až koncom roka 1938?",
            "Bahenná skákavá ryba", "Trigon", "Synanceja", "Latiméria divná", 6)
        addQuestion("Najnebezpečnejšiou rybou na svete je: ", "Žralok veľrybí", "Žralok tygrí", "Striekač Toxotes jaculator", "Žralok belavý", 6)
        addQuestion("Včelý sú bezstavovce: ", "S najlepším farebným videním", "Najsilnejšie", "Najhlučnejšie", "Najužitočnejšie", 6)
        addQuestion("Najsilnejšími bezstavovcami sú: ", "Komáre", "Pásovky", "Blchy", "Chrobáky", 6)
        addQuestion("Vtáčkár je: ", "Rybou žijúcou na amerických pobrežiach", "Pavúkom s najväčšími sieťami",
            "Pavúk loviaci najväčšie koristi", "Najťažším pavúkom", 6)
        addQuestion("Reakčný čas muchy je koľkokrát rychlejší ako reakčný čas človeka?", "35 krát", "90 krát", "15 krát", "12 krát", 6)
        addQuestion("Najjedovatejšiou medúzou (a pravdepodobne aj živočíchom celkovo) je: ", "Mesačná medúza", "Tanierovka arktická", "Kalmár", "Štvorhranovec", 6)
        //endregion
    }

    //  Adding category to database
    fun addCategory(name: String, picture: ByteArray, icon_picture: ByteArray){
        val cv = ContentValues()
        cv.put(CATEGORIES_NAME, name)
        cv.put(CATEGORIES_PICTURE, picture)
        cv.put(CATEGORIES_ICON_PICTURE, icon_picture)
        database!!.insert(TABLE_CATEGORIES, null, cv)
    }

    //  Deleting category
    fun deleteCategory(id: Int){
        database = writableDatabase
        database!!.delete(TABLE_CATEGORIES, "$CATEGORIES_ID=$id", null)
    }

    //  Getting category by id, used only in this Database Helper
    private fun getCategoryByID(id: Int): CCategory {
        var category: CCategory? = null
        val c: Cursor = database!!.rawQuery("SELECT * FROM $TABLE_CATEGORIES WHERE $CATEGORIES_ID=$id", null)
        if (c.moveToFirst()){
            category = CCategory(
                id = c.getInt(c.getColumnIndex(CATEGORIES_ID)),
                name = c.getString(c.getColumnIndex(CATEGORIES_NAME)),
                image = c.getBlob(c.getColumnIndex(CATEGORIES_PICTURE)),
                icon_image = c.getBlob(c.getColumnIndex(CATEGORIES_ICON_PICTURE))
            )
        }
        c.close()
        return category!!
    }

    //  Add question
    fun addQuestion(question: String, option_a: String, option_b: String, option_c: String, option_correct: String, cat_id: Int){
        val cv = ContentValues()
        cv.put(QUESTIONS_QUESTION, question)
        cv.put(QUESTIONS_OPTION_A, option_a)
        cv.put(QUESTIONS_OPTION_B, option_b)
        cv.put(QUESTIONS_OPTION_C, option_c)
        cv.put(QUESTIONS_OPTION_CORRECT, option_correct)
        cv.put(CATEGORIES_ID, cat_id)
        database!!.insert(TABLE_QUESTIONS, null, cv)
    }

    //  Get questions by type from database
    fun getQuestionsByType(category_id: Int): ArrayList<CQuestion>{
        val list = ArrayList<CQuestion>()
        database = readableDatabase

        val c: Cursor = database!!.rawQuery("SELECT * FROM $TABLE_QUESTIONS WHERE $CATEGORIES_ID = $category_id", null)
        if (c.moveToFirst()){
            do {
                val question = CQuestion(
                    id = c.getInt(c.getColumnIndex(QUESTIONS_ID)),
                    question = c.getString(c.getColumnIndex(QUESTIONS_QUESTION)),
                    optionA = c.getString(c.getColumnIndex(QUESTIONS_OPTION_A)),
                    optionB = c.getString(c.getColumnIndex(QUESTIONS_OPTION_B)),
                    optionC = c.getString(c.getColumnIndex(QUESTIONS_OPTION_C)),
                    optionCorrect = c.getString(c.getColumnIndex(QUESTIONS_OPTION_CORRECT)),
                    category = getCategoryByID(c.getInt(c.getColumnIndex(CATEGORIES_ID)))
                )
                list.add(question)
            }while (c.moveToNext())
        }
        c.close()

        return list
    }

    //  Update question by ID
    fun updateQuestion(question_id: Int, question_question: String, question_optionA: String, question_optionB: String,
                       question_optionC: String, question_optionCorrect: String, category_id: Int){
        database = writableDatabase
        val cv = ContentValues()
        cv.put(QUESTIONS_ID, question_id)
        cv.put(QUESTIONS_QUESTION, question_question)
        cv.put(QUESTIONS_OPTION_A, question_optionA)
        cv.put(QUESTIONS_OPTION_B, question_optionB)
        cv.put(QUESTIONS_OPTION_C, question_optionC)
        cv.put(QUESTIONS_OPTION_CORRECT, question_optionCorrect)
        cv.put(CATEGORIES_ID, category_id)
        database!!.update(TABLE_QUESTIONS, cv, "$QUESTIONS_ID=$question_id", null)
    }

    //  Delete question by ID
    fun deleteQuestion(question_id: Int){
        database = writableDatabase
        database!!.delete(TABLE_QUESTIONS, "$QUESTIONS_ID=$question_id", null)
    }

    //  Converting image to byte array, used in filling Tables
    private fun imageToByteArray(image: Int): ByteArray{
        val icon = BitmapFactory.decodeResource(c.resources, image)

        val stream = ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG,0, stream)
        return stream.toByteArray()
    }

    companion object{
        const val DATABASE_NAME = "Quiz.database"
        const val TABLE_QUESTIONS = "questions_table"
        const val TABLE_CATEGORIES = "categories_table"
        const val QUESTIONS_ID = "question_id"
        const val QUESTIONS_QUESTION = "question"
        const val QUESTIONS_OPTION_A = "option_a"
        const val QUESTIONS_OPTION_B = "option_b"
        const val QUESTIONS_OPTION_C = "option_c"
        const val QUESTIONS_OPTION_CORRECT = "option_correct"
        const val CATEGORIES_ID = "category_id"
        const val CATEGORIES_NAME = "name"
        const val CATEGORIES_PICTURE = "picture"
        const val CATEGORIES_ICON_PICTURE = "icon_picture"
    }
}