import java.util.Stack;
import java.util.HashMap;
import java.util.Collection;

/**
 * Classe décrivant un joueur
 *
 * @author Rayan HABCHI
 */
public class Player
{
    private UserInterface aGui;
    private Room aCurrentRoom;
    private Stack<Room> aPreviousRooms;
    private ItemList aInventory;
    private int aMaxWeight;
    private int aMoney;
    
    /**
     * Instancie un objet Player
     */
    public Player(){
        this.aPreviousRooms = new Stack<Room>();
        this.aInventory = new ItemList();
        this.aMaxWeight = 100;//par defaut
        this.aMoney = 5;
    }//Player()
    
    /**
     * Accède à la liste des dernières salles dans lesquelles le joueur a été
     * @return Stack<Room> la liste des salles
     */
    public Stack<Room> getPreviousRooms(){
        return this.aPreviousRooms;
    }//getPreviousRooms()
    
    /**
     * Accède à la salle dans laquelle le joueur se trouve
     * @return Room l'attribut aCurrentRoom
     */
    public Room getCurrentRoom(){
        return this.aCurrentRoom;
    }//getCurrentRoom()
    
    /**
     * Donne une chaine de caractères resumant le contenu de l'inventaire et son poids
     * @return String
     */
    public String getStringInventory(){
        if( this.aInventory.isEmpty() ){return "\nVotre inventaire est vide, vous avez " + this.aMoney + " pieces d'or. ";}
        String vD = "\nIl y'a dans votre inventaire : (" + this.aInventory.totalWeight() + "kg/" + this.aMaxWeight + "kg)\n";
        vD += this.aInventory.getString();
        vD += "vous avez " + this.aMoney + " pieces d'or. ";
        return vD;
    }//getStringInventory()
    
    /**
     * Donne une chaine de caractères resumant le contenu de l'inventaire et son poids
     * @return String
     */
    public void setCurrentRoom( final Room pR){
        this.aCurrentRoom = pR;
    }//setCurrentRoom( final Room pR)

    /**
     * Initialise l'attribut UserInterface pour permettre les affichages
     */
    public void setGUI( final UserInterface pGui){
        this.aGui = pGui;
    }//setGUI( final UserInterface pGui)
    
    /**
     * Deplace le joueur vers une autre salle
     * @param Room la salle dans laquelle le joueur est déplacé
     */
    public void moveRoom( final Room pR){
        this.aPreviousRooms.push(this.aCurrentRoom);
        this.aCurrentRoom = pR;
    }//moveRoom( final Room pR)
    
    /**
     * Deplace le joueur vers la derniere salle dans laquelle il a été
     */
    public void moveBackRoom(){
        if (this.aCurrentRoom.isExit(this.aPreviousRooms.peek())){
            this.aCurrentRoom = this.aPreviousRooms.pop();
        }
        else{
            this.aGui.println("vous ne pouvez pas traverser une trappe dans l'autre sens.");
        }
    }//moveBackRoom()
    
    /**
     * Permet au joueur de ramasser un objet (si il a encore assez de place dans l'inventaire)
     * String pItemName le nom de l'Item à rammasser
     */
    public void take( final String pItemName){
        if(this.aCurrentRoom.getItem(pItemName) != null){
            if( this.aInventory.totalWeight() + this.aCurrentRoom.getItem(pItemName).getWeight() <= this.aMaxWeight ){
                this.aInventory.addItem( this.aCurrentRoom.getItem(pItemName));
                this.aGui.println("Vous avez recuperé " + this.aCurrentRoom.getItem(pItemName).getDescription() + ".");
                this.aCurrentRoom.removeItem(pItemName);
            }
            else{
                this.aGui.println("Vous avez atteint le poids maximum de votre inventaire.");
            }
        }
        else{
            this.aGui.println("Il n'y a pas d'objet portant ce nom dans cette salle.");
        }
    }//take( final String pItemName)
    
    /**
     * Permet au joueur de lacher un objet
     * String pItemName le nom de l'Item à lacher
     */
    public void drop( final String pItemName){
        if(this.aInventory.containsItem(pItemName)){
            this.aCurrentRoom.addItem(this.aInventory.getItem(pItemName));
            this.aGui.println("Vous avez laché " + this.aInventory.getItem(pItemName).getDescription() + ".");
            this.aInventory.removeItem(pItemName);
        }
        else{
            this.aGui.println("Votre inventaire ne contient pas cet objet.");
        }
    }//drop( final String pItemName)
    
    /**
     * Fais consommer un cookie magique au joueur ( si il l'a dans l'inventaire ) ce qui double sa charge maximale d'inventaire 
     */
    public void cookie(){
        if(this.aInventory.containsItem("cookie")){
            this.aMaxWeight += this.aMaxWeight;
            this.aGui.println("Vous avez mangé le cookie magique: +100% de charge max d'inventaire.");
            this.aInventory.removeItem("cookie");
        }
        else{
            this.aGui.println("Vous n'avez pas de cookie dans votre inventaire.");
        }
    }//cookie()
    
    public void charge(){
        if(this.aInventory.containsItem("beamer")){
            if(this.aInventory.getItem("beamer") instanceof Beamer){
                //Item vB = this.aInventory.getItem("beamer");
                //if(vB == null){this.aGui.println( "YOYOYOYOYOYYOY" );}
                if( ((Beamer)this.aInventory.getItem("beamer")).charge(this.aCurrentRoom) ){
                    this.aGui.println("le beamer a mémorisé la salle.");
                }
                else{
                    this.aGui.println("le beamer est déjà chargé.");
                }
            }
        }
        else{
            this.aGui.println("vous n'avez pas de beamer.");
        }
    }

    public boolean fire(){
        if(this.aInventory.containsItem("beamer")){
            if(this.aInventory.getItem("beamer") instanceof Beamer){
                if( ((Beamer)this.aInventory.getItem("beamer")).fire(this) ){
                    this.aGui.println("Vous avez été téléporté, le beamer est maintenant déchargé.");
                    return true;
                }
                else{
                    this.aGui.println("le beamer n'est pas chargé.");
                }
            }
        }
        else{
            this.aGui.println("vous n'avez pas de beamer.");
        }
        return false;
    }
    
    public void sell( final String pName){
        if(!isNPCHere("Isaak")){
            return;
        }
        if(pName.equals("diamant")){
            if(this.aInventory.containsItem(pName)){
                this.aInventory.removeItem(pName);
                this.aMoney += 100;
                this.aGui.println("-Quel beau trésor, je vous l'achete pour 100 pièces d'or ");
            }
            else{
                this.aGui.println("Vous n'avez pas de diamant sur vous. ");
            }
        }
        else if(pName.equals("emeraude")){
            if(this.aInventory.containsItem(pName)){
                this.aInventory.removeItem(pName);
                this.aGui.println("-Quel beau trésor, je vous l'achete pour 50 pièces d'or ");
                this.aMoney += 50;
            }
            else{
                this.aGui.println("Vous n'avez pas d'emeraude sur vous. ");
            }
        }
        else{
            this.aGui.println("-Je ne suis pas interressé, seules les pierres précieuses m'interressent. ");
        }
    }
    
    public void buy( final String pName){
        if(!isNPCHere("Isaak")){
            return;
        }
        if(pName.equals("pain")){
            if(this.aMoney < 1){
                this.aGui.println("Vous n'avez pas assez d'argent.");
            }
            else{
                this.aMoney -= 1;
                this.aGui.println("l'objet acheté a été ajouté à votre inventaire.");
                Item vI = new Item("pain","du pain paysan bien nourrissant",1);
                this.aInventory.addItem(vI);
            }
        }
        else if(pName.equals("bouclier")){
            if(this.aMoney < 20){
                this.aGui.println("Vous n'avez pas assez d'argent.");
            }
            else{
                this.aMoney -= 20;
                this.aGui.println("l'objet acheté a été ajouté à votre inventaire.");
                Item vI = new Item("bouclier","un equipement de défense",10);
                this.aInventory.addItem(vI);
            }
        }
        else if(pName.equals("épée")){
            if(this.aMoney < 120){
                this.aGui.println("Vous n'avez pas assez d'argent.");
            }
            else{
                this.aMoney -= 120;
                this.aGui.println("l'objet acheté a été ajouté à votre inventaire.");
                Item vI = new Item("épée","une arme tranchante",5);
                this.aInventory.addItem(vI);
            }
        }
        else{
            this.aGui.println("Je ne vends pas cet objet");
        }
    }
    
    public void trade(){
        if(!isNPCHere("Merlin")){
            return;
        }
        if(this.aInventory.containsItem("épée")&&this.aInventory.containsItem("parchemin")){
            Item vI = new Item("épée_magique", "une arme magique très puissante", 5);
            this.aInventory.addItem(vI);
            this.aGui.println("Voilà pour vous, une épée enchantée par mon plus grand sortilège.");
            this.aInventory.removeItem("parchemin");
            this.aInventory.removeItem("épée");
        }
        else{
            this.aGui.println("Vous n'avez pas les objets dont j'ai besoin.");
        }
    }
    
    public boolean fight(){
        if(this.aInventory.containsItem("épée_magique")){
            return true;
        }
        return false;
    }
    
    public boolean isNPCHere( final String pNPCName){
        if(this.aCurrentRoom.getNPC() == null){
            this.aGui.println("Il n'y a personne dans cette salle.");
            return false;
        }
        else if(!this.aCurrentRoom.getNPC().getName().equals(pNPCName)){
            this.aGui.println(pNPCName +" n'est pas là.");
            return false;
        }
        return true;
    }
} //Player
