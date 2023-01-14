/**
 * Classe CommandWords : Verifie si les commandes sont valides par rapport 
 * à sa liste de commandes valides.
 *
 * @author  Michael Kolling and David J. Barnes + D.Bureau
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords
{
    // a constant array that will hold all valid command words
    private final String[] aValidCommands = {
        "aller", "aide", "quitter", "regarder", "manger", "retour", "prendre", "lacher", "inventaire", "test", "charger", "tirer", "parler", "acheter", "vendre", "échanger", "combattre"
    };

    public CommandWords()
    {} // CommandWords()

    /**
     * Verifie que la commande est valide
     * @param pString String contenant la commmande
     * @return true si la commande est valide, false sinon.
     */
    public boolean isCommand( final String pString )
    {
        for ( int vI=0; vI<this.aValidCommands.length; vI++ ) {
            if ( this.aValidCommands[vI].equals( pString ) )
                return true;
        } // for
        return false;
    } // isCommand()
    
    /**
     * accède à toutes les commandes valides et les retourne dans une chaine de caractère
     * @return String la liste des commandes valides
     */
    public String getValidCommands(){
        String vS = "";
        for(String vCommand : aValidCommands){
            vS = vS + vCommand + " ";
        }
        return vS;
    }//getValidCommands()
    
} // CommandWords
