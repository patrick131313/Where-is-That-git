package com.patrick.whereisthat.levelsDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 2/13/2018.
 */

public class Values {

    private List<Level> levelList;
    public Values() {

        levelList=new ArrayList<>();

        levelList.add(new Level("1","berlin","52.520007","13.404954","Berlin","Capital of Germany."));
        levelList.add(new Level("1","bern" ,"46.947974","7.447447","Bern","Capital of Switzerland."));
        levelList.add(new Level("1","bucuresti","44.426767", "26.102538", "București", "Capital of Romania."));
        levelList.add(new Level("1","edinburgh","55.953252","-3.188267","Edinburgh","Capital of Scotland"));
        levelList.add(new Level("1","luxembourg","49.611621","6.131935","Luxembourg","Capital of Luxembourg."));
        levelList.add(new Level("1","oslo","59.913869","10.752245","Oslo","Capital of Norway."));
        levelList.add(new Level("1","praga","50.075538","14.437800","Praga","Capital of Czech Republic."));
        levelList.add(new Level("1","roma","41.902783","12.496366","Roma","Capital of Italy."));
        levelList.add(new Level("1","tirana","41.327546","19.818698","Tirana","Capital of Albania."));
        levelList.add(new Level("1", "wien","48.208174","16.373819","Wien","Capital of Austria."));

        levelList.add(new Level("2","acropolis","37.970532","23.729276","–", "An ancient citadel located on a rocky outcrop above the Greek capital."));
        levelList.add(new Level("2","brandenburg","52.516275","13.377704", "–","One of the best-known landmarks of Germany."));
        levelList.add(new Level("2","st_basil","55.752523","37.623087", "–","Is a symbol of traditional Russian architecture."));
        levelList.add(new Level("2","colosseum","41.890210","12.492231","–","Is the largest ancient amphitheater that can be visited in Italy."));
        levelList.add(new Level("2","eiffel","48.858370","2.294481","–","Is the most-visited paid monument in the world."));
        levelList.add(new Level("2","sagrada","41.403630","2.174356","–","Is a large unfinished Roman Catholic church in the capital of Catalonia."));
        levelList.add(new Level("2","stonehenge","51.178882","-1.826215","–","Is a prehistoric monument in Wiltshire."));
        levelList.add(new Level("2","pisa","43.722952","10.396597","–","The most famous sloping building in the world."));
        levelList.add(new Level("2","versailles","48.804865","2.120355","–","Was the principal residence of the Kings of France."));
        levelList.add(new Level("2","westminster","51.499479","-0.124809","–","Is the meeting place of the House of Commons and the House of Lords."));

        levelList.add(new Level("3","batumi_tower","41.654401","41.638757","–","Is the tallest building in Georgia."));
        levelList.add(new Level("3","commerzbank_tower","50.110556","8.674167","-","It had been the tallest building in Europe until 2003."));
        levelList.add(new Level("3","dc_tower","48.231944","16.412778","-","Also known as Donau City Towers."));
        levelList.add(new Level("3","federation_tower","55.749693","37.537539","–","Is currently the tallest completed skyscraper in Europe."));
        levelList.add(new Level("3","sapphire_tower","41.085083","29.006454", "–","Was the fourth tallest building in Europe when its construction was completed in 2010."));
        levelList.add(new Level("3","sky_tower","51.094519","17.019570","–","Is the tallest building in Poland."));
        levelList.add(new Level("3","spire","52.232258","20.984269","–","Is the second tallest building in Poland."));
        levelList.add(new Level("3","the_shard","51.504500","-0.086500","–","The fifth tallest building in Europe."));
        levelList.add(new Level("3","folkart","38.454187","27.176769","–","Are twin skyscrapers in the Bayraklı district."));
        levelList.add(new Level("3","unicredit","45.483585","9.190051","–","Is the tallest building in Italy."));

        levelList.add(new Level("4","antwerpen","51.219448","4.402464","Antwerpen","It is Europe’s second-largest seaport, after Rotterdam."));
        levelList.add(new Level("4","barcelona","41.385064","2.173403","Barcelona","It is the capital and largest city of Catalonia."));
        levelList.add(new Level("4","birmingham","52.486243","-1.890401","Birmingham","Is a city of the county of West Midlands."));
        levelList.add(new Level("4","krakow","50.064650","19.944980","Kraków","Is the second largest and one of the oldest cities in Poland."));
        levelList.add(new Level("4","den_haag","52.070498","4.300700","Den Haag","Is the capital of the province of South Holland."));
        levelList.add(new Level("4","goteborg","57.708870","11.974560","Göteborg","It is the fifth largest city in the Nordic countries"));
        levelList.add(new Level("4","marseille","43.296482","5.369780","Marseille","Is the largest city of the Provence historical region."));
        levelList.add(new Level("4","munchen","48.135125","11.581980","Munchen","Is the capital of Bavaria."));
        levelList.add(new Level("4","napoli","40.851775","14.268124","Napoli","Is the regional capital of Campania."));
        levelList.add(new Level("4","glasgow","55.864237","-4.251806","Glasgow","Is the largest city in Scotland."));

        levelList.add(new Level("5","atomium","50.894941","4.341547","–","Was the main pavilion and iconic image of the World Fair of 1958 (Expo 58)."));
        levelList.add(new Level("5","basilica_sanpietro","41.902167","12.453937","–","Is an Italian Renaissance church, the papal enclave within the city of Rome."));
        levelList.add(new Level("5","bran","45.514902","25.367164","–","Commonly known as 'Dracula's Castle'."));
        levelList.add(new Level("5","golden_gate","50.448832","30.513332","–","It was the main gate in the 11th century fortifications of the capital of Kyivska Rus'."));
        levelList.add(new Level("5","mica_sirena","55.692860","12.599283","–","Is among iconic statues that symbolize cities like: Manneken Pis in, the Statue of Liberty and Christ the Redeemer."));
        levelList.add(new Level("5","saint_michel","48.636063","-1.511457","–","It is in the Bay of Saint-Malo, where the most representative phenomenon of flux and reflux is manifested."));
        levelList.add(new Level("5","neuschwanstein","47.557574","10.749800","–","Is a 19th-century Romanesque Revival palace in southwest of Bavaria."));
        levelList.add(new Level("5","charles","50.086477","14.411437","-","Is a historic bridge that crosses the Vltava river."));
        levelList.add(new Level("5","schonbrunn","48.184865","16.312240","–"," Is one of the most important architectural, cultural, and historical monuments in Austria."));
        levelList.add(new Level("5","belem","38.691584","-9.215977","–","Is a fortified tower located in Portugal."));

        levelList.add(new Level("6","blahnjukur","63.977222","-19.068611","–","Is a volcano in the area of Landmannalaugar."));
        levelList.add(new Level("6","blue_grotto","35.820856","14.457409","–","A cavern located in the Southern Europe."));
        levelList.add(new Level("6","breidamerkurjoekull","64.153056","-16.400000","–","Is an outlet glacier of the larger glacier of Vatnajökull."));
        levelList.add(new Level("6","cazane","44.611406","22.271605","–","A sector in the Danube defile when passing through the Carpathian Mountains."));
        levelList.add(new Level("6","cliffs","52.971487","-9.430771","–","Are sea cliffs located at the southwestern edge of the Burren region in County Clare."));
        levelList.add(new Level("6","torcal","36.952151","-4.542279","–","Is a nature reserve in the Sierra del Torcal mountain range located south of the city of Antequera."));
        levelList.add(new Level("6","etna","37.751005","14.993435","-","Is an active stratovolcano on the east coast of Sicily."));
        levelList.add(new Level("6","rhinefalls","47.677951","8.615580","–","Are located on the High Rhine."));
        levelList.add(new Level("6","saarschleife","49.500674","6.545062","–","Is a breakthrough valley of the Saar through the Taunusquarzit and is one of the most famous attractions in the Saarland."));
        levelList.add(new Level("6","trolltunga","60.124167","6.740000","–","Is a rock formation situated about 1,100 metres above sea level in the municipality of Odda in Hordaland county."));

        levelList.add(new Level("7","coimbra","40.203314","-8.410257","Coimbra","Is the largest city of the district of Coimbra."));
        levelList.add(new Level("7","dijon","47.322047","5.041480","Dijon","Is the capital of the Côte-d'Or département."));
        levelList.add(new Level("7","kosice","48.716386","21.261075","Cașovia","It is situated on the river Hornád at the eastern reaches of the Slovak Ore Mountains."));
        levelList.add(new Level("7","salonic","40.640063","22.944419","Thessaloniki","Is the capital of Greek Macedonia."));
        levelList.add(new Level("7","salzburg","47.809490","13.055010","Salzburg","Is the fourth-largest city in Austria."));
        levelList.add(new Level("7","split","43.508132","16.440193","Split","Is the largest city of the region of Dalmatia."));
        levelList.add(new Level("7","tatabanya","47.569246","18.404818","Tatabánya","It is the capital of Komárom-Esztergom County."));
        levelList.add(new Level("7","timisoara","45.748872","21.208679","Timișoara","Is the informal capital city of the historical region of Banat."));
        levelList.add(new Level("7","varna","43.214050","27.914733","Varna","Is the third-largest city in Bulgaria."));
        levelList.add(new Level("7","wroclaw","51.107885","17.038538","Wrocław","Is the historical capital of Silesia and Lower Silesia."));

        levelList.add(new Level("8","arc_triumf","47.024725","28.832551","–","Is an architectural monument built in the years 1840-1841, to commemorate the victory of the Russian armies over the Turks."));
        levelList.add(new Level("8","freedom_monument","56.951490","24.113304","–","It is considered an important symbol of the freedom, independence, and sovereignty."));
        levelList.add(new Level("8","guaita","43.935261","12.449917","–","It is one of the three towers depicted on both the national flag and coat of arms."));
        levelList.add(new Level("8","gutenberg","47.065200","9.500100","–","Is a preserved castle in the town of Balzers, the centre of the municipality of Balzers."));
        levelList.add(new Level("8","jvari","41.838116","44.733159","–","Stands on the rocky mountaintop at the confluence of the Mtkvari and Aragvi rivers."));
        levelList.add(new Level("8","kadriorg","59.438500","24.791000","–","Is a Petrine Baroque palace built for Catherine I of Russia."));
        levelList.add(new Level("8","maiden","40.366088","49.837269","–","Is a legendary place and world-famous landmark in the Caucasus region."));
        levelList.add(new Level("8","sant_joan","42.570832","1.607775","–","Is a church located in Canillo."));
        levelList.add(new Level("8","ancient","42.146922","24.751123","–","Is one of the world's best-preserved ancient theatres."));
        levelList.add(new Level("8","trakai","54.652312","24.933632","–","Is castle located on an island in Lake Galvė."));

        levelList.add(new Level("9","berat","40.708638","19.943731","Berat","Historically known as Poulcheriopólis and Antipatreia."));
        levelList.add(new Level("9","bernkastel","49.914598","7.074789","Bernkastel","It is in Rhineland-Palatinate."));
        levelList.add(new Level("9","bonifacio","41.387174","9.159269","Bonifacio","Is located directly on the Mediterranean Sea, separated from Sardinia."));
        levelList.add(new Level("9","dubrovnik","42.650661","18.094424","Dubrovnik","It is one of the most prominent tourist destinations in the Mediterranean Sea."));
        levelList.add(new Level("9","hallstatt","47.562234","13.649262","Hallstatt","Is a small village in the district of Gmunden."));
        levelList.add(new Level("9","kotor","42.424662","18.771234","Kotor","It is located in a secluded part of the Gulf of Kotor."));
        levelList.add(new Level("9","piran","45.528319","13.568289","Piran","Is a town on the Gulf of Piran on the Adriatic Sea."));
        levelList.add(new Level("9","positano","40.628053","14.484981","Positano","Is a village and comune on the Amalfi Coast, in Campania."));
        levelList.add(new Level("9","reine","67.932387","13.088733","Reine","It is located on the island of Moskenesøya in the Lofoten archipelago."));
        levelList.add(new Level("9","imerovigli","36.432771","25.422807","Imerovigli","Is a village on the island of Santorini."));

        levelList.add(new Level("10","bastei","50.961912","14.073166","–","Is a bridge formation above the Elbe River."));
        levelList.add(new Level("10","kappel","47.051649","8.307535","–","Is the oldest wooden covered bridge in Europe, as well as the world's oldest surviving truss bridge."));
        levelList.add(new Level("10","millau","44.077454","3.022839","–","It is the tallest bridge in the world."));
        levelList.add(new Level("10","moseltal","50.315001","7.494106","–","Is a bridge over a meander of the river Moselle, connecting the Hunsrück and Eifel mountain ranges."));
        levelList.add(new Level("10","olandsbron","56.674064","16.420919","–","It is one of the longest in all of Europe."));
        levelList.add(new Level("10","old_bridge","43.337285","17.815015","–","Is a rebuilt 16th-century Ottoman bridge that crosses the river Neretva."));
        levelList.add(new Level("10","ponte_vecchio","43.767925","11.253143","–","Is a medieval stone closed-spandrel segmental arch bridge over the Arno River."));
        levelList.add(new Level("10","puente_nuevo","36.740672","-5.165793","–","Is the newest and largest of three bridges that span the 120-metre (390 ft)-deep chasm that carries the Guadalevín River."));
        levelList.add(new Level("10","severn","51.573908","-2.698366","-","Is a bridge operated that spans the River Severn and River Wye between Aust and Chepstow."));
        levelList.add(new Level("10","vasco","38.758039","-9.037467","–","Is the longest bridge entirely within Europe."));

        levelList.add(new Level("11","bazarduzu","41.221667","47.858333","–","Is a mountain peak in the Greater Caucasus."));
        levelList.add(new Level("11","deravica","42.531808","20.139900","–"," Is the second highest mountain peak in the Prokletije mountain range."));
        levelList.add(new Level("11","gerlachovsky","49.164378","20.133598","-","Is the highest peak in the High Tatras."));
        levelList.add(new Level("11","kebnekaise","67.918679","18.572770","-","Is part of the Scandinavian Mountains."));
        levelList.add(new Level("11","midzor","43.394750","22.678801","-","Is a peak in the Balkan Mountains."));
        levelList.add(new Level("11","pico","38.468676","-28.399258","-","Is a stratovolcano located in the mid-Atlantic archipelago of the Azores."));
        levelList.add(new Level("11","rysy","49.179548","20.088064","-","Is a mountain in the crest of the High Tatras."));
        levelList.add(new Level("11","teide","28.272338","-16.642508","–","Is a volcano in the Canary Islands."));
        levelList.add(new Level("11","triglav","46.379199","13.833917","–","Is the highest peak of the Julian Alps."));
        levelList.add(new Level("11","zugspitze","47.421066","10.985365","–","Is the highest peak of the Wetterstein Mountains."));

    }
    public List<Level> getValues()
    {
        return levelList;
    }
}
