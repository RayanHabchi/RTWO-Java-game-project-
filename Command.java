/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author HABCHI Rayan
 */
public class Command
{
    private String aCommandWord;
    private String aSecondWord;
    
    /**
     * Instancie une commande
     * @param pC String pour initialiser aCommandWord
     * @param pS String pour initialiser aSecondWord
     */
    public Command(final String pC, final String pS){
        this.aCommandWord = pC;
        this.aSecondWord = pS;
    }//Command()
   
    /**
     * accède au premier mot de la commande
     * @return String le premier mot de la commande
     */
    public String getCommandWord(){
        return this.aCommandWord;
    }//getCommandWord()
    
    /**
     * accède au deuxieme mot de la commande
     * @return String le deuxieme mot de la commande
     */
    public String getSecondWord(){
        return this.aSecondWord;
    }//getSecondWord()
    
    /**
     * Vérifie qu'un second mot a bien été tapé
     * @return true si la commande contient un deuxieme mot, false sinon
     */
    public boolean hasSecondWord(){
        return (this.aSecondWord != null);
    }//hasSecondWord()
    
    /**
     * Verifie si la commande contient un mot
     * @return true si la commande contient un mot, false sinon
     */
    public boolean isUnknown(){
        return (this.aCommandWord == null);
    }//isUnknown()
    
    
} // Command
