
/**
 * Une classe pour implementer le beamer dans le jeu
 *
 * @author HABCHI Rayan
 * @version (un numéro de version ou une date)
 */
public class Beamer extends Item
{
    private Room aMemorizedRoom;

    /**
     * Constructeur d'objets de classe Beamer
     */
    public Beamer()
    {
        super( "beamer", "un objet servant à se téléporter", 5);
    }
    
    public boolean charge( final Room pR){
        if(this.aMemorizedRoom == null){
            this.aMemorizedRoom = pR;
            return true;
        }
        return false;
    }
    
    public boolean fire( final Player pPlayer){
        if(this.aMemorizedRoom != null){
            pPlayer.moveRoom(this.aMemorizedRoom);
            this.aMemorizedRoom = null;
            return true;
        }
        return false;
    }
}
