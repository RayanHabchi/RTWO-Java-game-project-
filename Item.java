
/**
 * Une classe servant à instancer des objets (=items) dans le jeu zuul.
 *
 * @author HABCHI Rayan
 */
public class Item
{
    //### ATTRIBUTS ###
    
    private String aName;
    private String aDescription;
    private int aWeight;
    
    //### CONSTRUCTEURS ###
    
    /**
     * Construit un Item de description pD et de poids pW
     * @param pD String pour initialiser aDescription
     * @param pW String pour initialiser aWeight
     */
    public Item( final String pN, final String pD, final int pW){
        this.aName = pN;
        this.aDescription = pD;
        this.aWeight = pW;
    }
    
    //### ACCESSEURS ###
    
    /**
     * Accede au nom de l'Item
     * @return l'attribut aName
     */
    public String getName(){
        return this.aName;
    }//getName
    
    /**
     * Accede à la description de l'Item
     * @return l'attribut aDescription
     */
    public String getDescription(){
        return this.aDescription;
    }//getDescription()
    
    /**
     * Accède au poids de l'Item
     * @return l'attribut aWeight
     */
    public int getWeight(){
        return this.aWeight;
    }//getWeight()
}// Item
