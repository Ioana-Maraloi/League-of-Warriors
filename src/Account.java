import java.util.ArrayList;
import java.util.SortedSet;

public class Account {
    private Account.Information information;
    public ArrayList<CharacterCls> character;
    public int gamesNumber;

    public Account(ArrayList<CharacterCls> characters, int gamesNumber) {
        this.gamesNumber = gamesNumber;
        this.character = characters;
    }
    public void setInformation(String name, Credentials credentials, String country, SortedSet favorites) {
        information = Account.InformationBuilder.newInstance().Name(name).Credentials(credentials).Country(country).Favorites(favorites).build();
    }
    public boolean account(String email){
        return information.credentials.getEmail().equals(email);
    }
    public boolean logIn(String password) {
        return this.information.credentials.getPassword().equals(password);
    }
    public String getName(){
        return information.name;
    }
    public int getCharacters(){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < character.size(); i++){
            output.append(i + 1).append(". ").append(character.get(i).toString()).append("\n");
        }
        System.out.print(output);
        return character.size();
    }
    static class Information{
//        required parameters
        private Credentials credentials;
        private String name;
//        optional parameters
        private String country;
        private SortedSet favorites;
        private Information(InformationBuilder builder){
            this.credentials = builder.credentials;
            this.name = builder.name;
            this.country = builder.country;
            this.favorites = builder.favorites;
        }

    }
    static class InformationBuilder{
//        required parameters
        private String name;
        private Credentials credentials;
//        optional parameters
        private String country;
        private SortedSet favorites;
        private InformationBuilder() {

        }
        public static InformationBuilder newInstance(){
            return new InformationBuilder();
        }
        public InformationBuilder(String name, Credentials credentials){
            this.name = name;
            this.credentials = credentials;
        }
        public InformationBuilder Country(String country){
            this.country = country;
            return this;
        }
        public InformationBuilder Favorites(SortedSet favorites){
            this.favorites = favorites;
            return this;
        }
        public InformationBuilder Name(String name){
            this.name = name;
            return this;
        }
        public InformationBuilder Credentials(Credentials credentials){
            this.credentials = credentials;
            return this;
        }
        public Information build(){
            return new Information(this);
        }

    }
}
