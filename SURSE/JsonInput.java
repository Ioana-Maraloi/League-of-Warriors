import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.simple.JSONValue;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
//import org.json.simple.JSONException;
import org.json.simple.JSONObject;


public class JsonInput {
    ArrayList<Account> accounts;
    public JsonInput() {
        super();
        accounts = deserializeAccounts();
    }
    public static ArrayList<Account> deserializeAccounts() {
        String accountPath = "SURSE/accounts.json";
        try {
            String content = new String((Files.readAllBytes(Paths.get(accountPath))));
            Object obj = JSONValue.parse(content);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray accountsArray = (JSONArray) jsonObject.get("accounts");
            ArrayList<Account> accounts = new ArrayList<>();
            for (int i = 0; i < accountsArray.size(); i++) {
                 JSONObject accountJson = (JSONObject) accountsArray.get(i);
                // name, country, games_number
                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int gamesNumber = Integer.parseInt((String)accountJson.get("maps_completed"));

                // Credentials
                Credentials credentials;
                JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                String email = (String) credentialsJson.get("email");
                String password = (String) credentialsJson.get("password");

                credentials = new Credentials(email, password);

                // Favorite games
                SortedSet<String> favoriteGames = new TreeSet();
                JSONArray games = (JSONArray) accountJson.get("favorite_games");
                for (int j = 0; j < games.size(); j++) {
                    favoriteGames.add((String) games.get(j));
                }

                // Characters
                ArrayList<CharacterCls> characters = new ArrayList<>();
                JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                for (int j = 0; j < charactersListJson.size(); j++) {
                    JSONObject charJson = (JSONObject) charactersListJson.get(j);
                    String cname = (String) charJson.get("name");
                    String profession = (String) charJson.get("profession");
                    String level = (String) charJson.get("level");
                    int lvl = Integer.parseInt(level);
                    String experience = charJson.get("experience").toString();
                    Integer experience_int = Integer.parseInt(experience);

//                    CharacterCls newCharacter = null;
                    CharacterCls newCharacter = CharacterFactory.getCharacter(profession, cname, experience_int, lvl);
//                    if (profession.equals("Warrior"))
//                        newCharacter = new Warrior(cname, experience_int, lvl);
//                    if (profession.equals("Rogue"))
//                        newCharacter = new Rogue(cname, experience_int, lvl);
//                    if (profession.equals("Mage"))
//                        newCharacter = new Mage(cname, experience_int, lvl);
                    characters.add(newCharacter);
                }

                Account account = new Account(characters, gamesNumber);
                account.setInformation(name, credentials, country, favoriteGames);
//                account.information = Account.InformationBuilder.newInstance().Name(name).Credentials(credentials).Country(country).Favorites(favoriteGames).build();
//                Account.Information information = new Account.InformationBuilder(name, credentials).setCountry(country).setFavorites(favoriteGames).build();
                accounts.add(account);
            }
            return accounts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}