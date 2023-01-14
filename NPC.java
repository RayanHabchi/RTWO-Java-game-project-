
/**
 * Une classe permettant la géstion des personnages non joueurs
 *
 * @author HABCHI Rayan
 * @version (un numéro de version ou une date)
 */
public class NPC
{
    private String aName;
    private String aDescription;
    private String aDialogue;

    /**
     * Constructeur d'objets de classe NPC
     */
    public NPC( final String pName, final String pDescription, final String pDialogue){
        this.aName = pName;
        this.aDescription = pDescription;
        this.aDialogue = pDialogue;
    }
    
    public String getName(){
        return this.aName;
    }
    
    public String getDescription(){
        return this.aDescription;
    }
    
    public String getDialogue(){
        return this.aDialogue;
    }
    
}
