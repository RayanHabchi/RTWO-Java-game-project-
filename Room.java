import java.util.Set;
import java.util.HashMap;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author HABCHI Rayan
 */
public class Room
{   
    //### ATTRIBUTS ###
    private String aDescription;
    private HashMap<String, Room> aExits;
    private String aImageName;
    private ItemList aItems;
    private NPC aNPC;
    
    //### CONSTRUCTEURS ###
    
    /**
     * Construit une salle de description pD, ses sorties seront initialisées plus tard
     * @param pD String pour initialiser aDescription
     */
    public Room(final String pD, final String pI){
        this.aDescription = pD;
        aExits = new HashMap<String, Room>();
        this.aImageName = pI;
        aItems = new ItemList();
    }//Room()
    
    //### ACCESSEURS ###
    
    public NPC getNPC(){
        return this.aNPC;
    }
    
    /**
     * @return l'attribut aDescription
     */
    public String getDescription(){
        return this.aDescription;
    }//getDescription()
    
    /**
     * @return une description détailléee de la pièce et les differentes sorties
     */
    public String getLongDescription(){ 
        return "Vous êtes " + aDescription + ".\n" + getExitString()
        + this.getLongItemDescription() + this.getNPCDescription();
    }//getLongDescription()
    
    /**
     * @return une description détailléee de l'objet et son poids
     */
    public String getLongItemDescription(){
        if( this.aItems.isEmpty() ){return "\nIl n'a pas d'objet dans cette salle. ";}
        String vD = "\nIl y'a dans cette salle:\n";
        vD += this.aItems.getString();
        return vD;
    }//String getLongItemDescription()
    
    public String getNPCDescription(){
        if(this.aNPC != null){return "Il y a "+ this.aNPC.getName() + ", " + this.aNPC.getDescription() + ".";}
        return "";
    }
    
    /**
     * Accède à la Room dans la direction pDirection (par rapport à l'Objet courant)
     * @param pDirection String exemple : north, south, up...
     * @return la Room dans la direction pDirection (par rapport à l'Objet courant)
     */
    public Room getExit( final String pDirection){
        return aExits.get(pDirection);
    }//getExit(String pDirection)
    
    public boolean isExit( final Room pR){
        return this.aExits.containsValue(pR);
    }
    
    /**
     * Accède dans l'ItemList à l'Item dont le nom est passé en paramètre (s'il existe)
     * @param String le nom de l'Item
     * @return Item l'Item auquel qu'on a trouvé (ou pas) dans l'ItemList
     */
    public Item getItem( final String pItemName){
        return aItems.getItem(pItemName);
    }//getItem( final String pItemName)
    
    /**
     * Supprime dans l'ItemList l'Item dont le nom est passé en paramètre (s'il existe)
     * @param String le nom de l'Item à supprimer
     */
    public void removeItem( final String pItemName){
        aItems.removeItem(pItemName);
    }//removeItem( final String pItemName)
    
    /**
     * Ajoute dans l'ItemList l'Item dont le nom est passé en paramètre
     * @param String le nom de l'Item
     */
    public void addItem( final Item pI){
        this.aItems.addItem( pI);
    }//addItem( final Item pI)
    
    /**
     * Accède à la liste des direction dans lesquelles le joueur peut se deplacer
     * @return String la liste des direction dans lesquelles le joueur peut se deplacer
     */
    public String getExitString(){
        String vExit = "Les sorties :";
        Set<String> keys = aExits.keySet();
        for(String exit : keys){
            vExit += " " + exit;
        }
        return vExit;
    }//String getExitString
    
    /**
     * Return a string describing the room's image name
     */
    public String getImageName(){
        return this.aImageName;
    }//getImageName()
    
    //### METHODES ###
    
    /**
     * Permet d'initialiser une sortie, donc mettre une salle dans une direction donnée
     * @param pD String La direction
     * @param pR Room La salle située dans cette direction
     */
    public void setExit(final String pD, final Room pR){
        aExits.put(pD,pR);
    }//setExits()
    
    public void setNPC(final NPC pNPC){
        this.aNPC = pNPC;
    }
    
} // Room
