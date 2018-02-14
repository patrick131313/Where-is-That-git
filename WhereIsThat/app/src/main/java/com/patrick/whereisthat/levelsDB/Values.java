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

        levelList.add(new Level("1","berlin","52.520007","13.404954","Berlin","Hint_berlin"));
        levelList.add(new Level("1","bern" ,"46.947974","7.447447","Bern","Hint_bern"));
        levelList.add(new Level("1","bucuresti","44.426767", "26.102538", "București", "Hint_bucuresti"));
        levelList.add(new Level("1","glasgow","55.864237","-4.251806","Glasgow","Hint_glasgow"));
        levelList.add(new Level("1","luxembourg","49.611621","6.131935","Luxembourg","Hint_luxembourg"));
        levelList.add(new Level("1","oslo","59.913869","10.752245","Oslo","Hint_oslo"));
        levelList.add(new Level("1","praga","50.075538","14.437800","Praga","Hint_praga"));
        levelList.add(new Level("1","roma","41.902783","12.496366","Roma","Hint_roma"));
        levelList.add(new Level("1","tirana","41.327546","19.818698","Tirana","Hint_tirana"));
        levelList.add(new Level("1", "wien","48.208174","16.373819","Wien","Hint_wien"));

        levelList.add(new Level("2","acropolis","37.970532","23.729276","–", "Hint_acropolis"));
        levelList.add(new Level("2","brandenburg","52.516275","13.377704", "–","Hint_brandenburg"));
        levelList.add(new Level("2","st_basil","55.752523","37.623087", "–","Hint_st.basil"));
        levelList.add(new Level("2","colosseum","41.890210","12.492231","–","Hint_colosseum"));
        levelList.add(new Level("2","eiffel","48.858370","2.294481","–","Hint_eiffel"));
        levelList.add(new Level("2","sagrada","41.403630","2.174356","–","Hint_sagrada"));
        levelList.add(new Level("2","stonehenge","51.178882","-1.826215","–","Hint_stonehenge"));
        levelList.add(new Level("2","pisa","43.722952","10.396597","–","Hint_pisa"));
        levelList.add(new Level("2","versailles","48.804865","2.120355","–","Hint_versailles"));
        levelList.add(new Level("2","westminster","51.499479","-0.124809","–","Hint_westminster"));

        levelList.add(new Level("3","batumi_tower","41.654401","41.638757","–","Hint_batumi"));
        levelList.add(new Level("3","commerzbank_tower","50.110556","8.674167","-","Hint_commerzbank"));
        levelList.add(new Level("3","dc_tower","48.231944","16.412778","-","Hint_dc"));
        levelList.add(new Level("3","federation_tower","55.749693","37.537539","–","Hint_federation"));
        levelList.add(new Level("3","sapphire_tower","41.085083","29.006454", "–","Hint_sapphire"));
        levelList.add(new Level("3","sky_tower","51.094519","17.019570","–","Hint_sky"));
        levelList.add(new Level("3","socar_tower","40.405512","49.883653","–","Hint_socar"));
        levelList.add(new Level("3","the_shard","51.504500","-0.086500","–","Hint_the_shard"));
        levelList.add(new Level("3","torre_cristal","40.478268","-3.686876","–","Hint_torre"));
        levelList.add(new Level("3","unicredit","45.483585","9.190051","–","Hint_unicredit"));

        levelList.add(new Level("4","antwerpen","51.219448","4.402464","Antwerpen","Hint_antwerpen"));
        levelList.add(new Level("4","barcelona","41.385064","2.173403","Barcelona","Hint_barcelona"));
        levelList.add(new Level("4","birmingham","52.486243","-1.890401","Birmingham","Hint_birmingham"));
        levelList.add(new Level("4","krakow","50.064650","19.944980","Kraków","Hint_krakow"));
        levelList.add(new Level("4","den_haag","52.070498","4.300700","Den Haag","Hint_den_haag"));
        levelList.add(new Level("4","goteborg","57.708870","11.974560","Göteborg","Hint_goteborg"));
        levelList.add(new Level("4","marseille","43.296482","5.369780","Marseille","Hint_marseille"));
        levelList.add(new Level("4","munchen","48.135125","11.581980","Munchen","Hint_munchen"));
        levelList.add(new Level("4","napoli","40.851775","14.268124","Napoli","Hint_napoli"));
        levelList.add(new Level("4","edinburgh","55.953252","-3.188267","Edinburgh","Hint_edinburgh"));

        levelList.add(new Level("5","atomium","50.894941","4.341547","–","Hint_atomium"));
        levelList.add(new Level("5","basilica_sanpietro","41.902167","12.453937","–","Hint_sanpietro"));
        levelList.add(new Level("5","bran","45.514902","25.367164","–","Hint_bran"));
        levelList.add(new Level("5","golden_gate","50.448832","30.513332","–","Hint_golden_gate"));
        levelList.add(new Level("5","mica_sirena","55.692860","12.599283","–","Hint_mica_sirena"));
        levelList.add(new Level("5","saint_michel","48.636063","-1.511457","–","Hint_saint_michel"));
        levelList.add(new Level("5","neuschwanstein","47.557574","10.749800","–","Hint_neuschwanstein"));
        levelList.add(new Level("5","charles","50.086477","14.411437","-","Hint_charles"));
        levelList.add(new Level("5","schonbrunn","48.184865","16.312240","–","Hint_schonbrunn"));
        levelList.add(new Level("5","belem","38.691584","-9.215977","–","Hint_belem"));

        levelList.add(new Level("6","blahnjukur","63.977222","-19.068611","–","Hint_ blahnjukur"));
        levelList.add(new Level("6","blue_grotto","35.820856","14.457409","–","Hint_grotto"));
        levelList.add(new Level("6","breidamerkurjoekull","64.153056","-16.400000","–","Hint_breidamerkurjoekull"));
        levelList.add(new Level("6","cazane","44.611406","22.271605","–","Hint_cazane"));
        levelList.add(new Level("6","cliffs","52.971487","-9.430771","–","Hint_cliffs"));
        levelList.add(new Level("6","torcal","36.952151","-4.542279","–","Hint_torcal"));
        levelList.add(new Level("6","etna","37.751005","14.993435","-","Hint_etna"));
        levelList.add(new Level("6","rhinefalls","47.677951","8.615580","–","Hint_rhine"));
        levelList.add(new Level("6","saarschleife","49.500674","6.545062","–","Hint_saarschleife"));
        levelList.add(new Level("6","trolltunga","60.124167","6.740000","–","Hint_trolltunga"));

        levelList.add(new Level("7","coimbra","40.203314","-8.410257","Coimbra","Hint_coimbra"));
        levelList.add(new Level("7","dijon","47.322047","5.041480","Dijon","Hint_dijon"));
        levelList.add(new Level("7","kosice","48.716386","21.261075","Cașovia","Hint_kosice"));
        levelList.add(new Level("7","salonic","40.640063","22.944419","Thessaloniki","Hint_salonic"));
        levelList.add(new Level("7","salzburg","47.809490","13.055010","Salzburg","Hint_salzburg"));
        levelList.add(new Level("7","split","43.508132","16.440193","Split","Hint_split"));
        levelList.add(new Level("7","tatabanya","47.569246","18.404818","Tatabánya","Hint_tatabanya"));
        levelList.add(new Level("7","timisoara","45.748872","21.208679","Timișoara","Hint_timisoara"));
        levelList.add(new Level("7","varna","43.214050","27.914733","Varna","Hint_varna"));
        levelList.add(new Level("7","wroclaw","51.107885","17.038538","Wrocław","Hint_wroclaw"));

        levelList.add(new Level("8","arc_triumf","47.024725","28.832551","–","Hint_arc"));
        levelList.add(new Level("8","freedom_monument","56.951490","24.113304","–","Hint_freedom"));
        levelList.add(new Level("8","guaita","43.935261","12.449917","–","Hint_guaita"));
        levelList.add(new Level("8","gutenberg","47.065200","9.500100","–","Hint_gutenberg"));
        levelList.add(new Level("8","jvari","41.838116","44.733159","–","Hint_jvari"));
        levelList.add(new Level("8","kadriorg","59.438500","24.791000","–","Hint_kadriorg"));
        levelList.add(new Level("8","maiden","40.366088","49.837269","–","Hint_maiden"));
        levelList.add(new Level("8","sant_joan","42.570832","1.607775","–","Hint_sant_joan"));
        levelList.add(new Level("8","ancient","42.146922","24.751123","–","Hint_ancient"));
        levelList.add(new Level("8","trakai","54.652312","24.933632","–","Hint_trakai"));

        levelList.add(new Level("9","berat","40.708638","19.943731","Berat","Hint_berat"));
        levelList.add(new Level("9","bernkastel","49.914598","7.074789","Bernkastel","Hint_bernkastel"));
        levelList.add(new Level("9","bonifacio","41.387174","9.159269","Bonifacio","Hint_bonifacio"));
        levelList.add(new Level("9","dubrovnik","42.650661","18.094424","Dubrovnik","Hint_dubrovnik"));
        levelList.add(new Level("9","hallstatt","47.562234","13.649262","Hallstatt","Hint_hallstatt"));
        levelList.add(new Level("9","kotor","42.424662","18.771234","Kotor","Hint_kotor"));
        levelList.add(new Level("9","piran","45.528319","13.568289","Piran","Hint_piran"));
        levelList.add(new Level("9","positano","40.628053","14.484981","Positano","Hint_positano"));
        levelList.add(new Level("9","reine","67.932387","13.088733","Reine","Hint_reine"));
        levelList.add(new Level("9","imerovigli","36.432771","25.422807","Imerovigli","Hint_imerovigli"));

        levelList.add(new Level("10","bastei","50.961912","14.073166","–","Hint_bastei"));
        levelList.add(new Level("10","kappel","47.051649","8.307535","–","Hint_kappel"));
        levelList.add(new Level("10","millau","44.077454","3.022839","–","Hint_millau"));
        levelList.add(new Level("10","moseltal","50.315001","7.494106","–","Hint_moseltal"));
        levelList.add(new Level("10","olandsbron","56.674064","16.420919","–","Hint_olandsbron"));
        levelList.add(new Level("10","old_bridge","43.337285","17.815015","–","Hint_oldbridge"));
        levelList.add(new Level("10","ponte_vecchio","43.767925","11.253143","–","Hint_ponte"));
        levelList.add(new Level("10","puente_nuevo","36.740672","-5.165793","–","Hint_puente"));
        levelList.add(new Level("10","severn","51.573908","-2.698366","-","Hint_severn"));
        levelList.add(new Level("10","vasco","38.758039","-9.037467","–","Hint_vasco"));

        levelList.add(new Level("11","bazarduzu","41.221667","47.858333","–","Hint_bazarduzu"));
        levelList.add(new Level("11","deravica","42.531808","20.139900","–","Hint_deravica"));
        levelList.add(new Level("11","gerlachovsky","49.164378","20.133598","-","Hint_ gerlachovsky"));
        levelList.add(new Level("11","kebnekaise","67.918679","18.572770","-","Hint_kebnekaise"));
        levelList.add(new Level("11","midzor","43.394750","22.678801","-","Hint_midzor"));
        levelList.add(new Level("11","pico","38.468676","-28.399258","-","Hint_pico"));
        levelList.add(new Level("11","rysy","49.179548","20.088064","-","Hint_rysy"));
        levelList.add(new Level("11","teide","28.272338","-16.642508","–","Hint_teide"));
        levelList.add(new Level("11","triglav","46.379199","13.833917","–","Hint_triglav"));
        levelList.add(new Level("11","zugspitze","47.421066","10.985365","–","Hint_zugspitze"));

    }
    public List<Level> getValues()
    {
        return levelList;
    }
}
