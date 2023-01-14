import java.util.HashMap;
import java.util.Collection;
/**
 * Une classe gerant une collection d'Item, servant dans la gestion des salles et des joueurs
 *
 * @author HABCHI Rayan
 */
public class ItemList
{
    private HashMap<String,Item> aItems;
    
    /**
     * Instancie une ItemList (Collection d'Item)
     **/
    public ItemList(){
        this.aItems = new HashMap<String,Item>();
    }//ItemList()
    
    /**
     * Indique si l'ItemList est vide ou non
     * @return true si l'ItemList est vide, false sinon
     **/
    public boolean isEmpty(){
        return this.aItems.isEmpty();
    }//isEmpty()
    
    /**
     * Indique si l'ItemList contient un Item en particulier
     * @param String pItemName le nom de l'Item à rechercher
     * @return true si l'ItemList contient l'Item passé en paramètre, false sinon
     **/
    public boolean containsItem( final String pItemName){
        return this.aItems.containsKey(pItemName);
    }//containsItem( final String pItemName)
    
    /**
     * Accède à un Item dans l'ItemList (si il est contenu dedans)
     * @param String pItemName le nom de l'Item à acceder
     * @return L'Item si il est dans l'ItemList (ou pas)
     **/
    public Item getItem( final String pItemName){
        return this.aItems.get(pItemName);
    }//getItem( final String pItemName)
    
    /**
     * Ajoute un Item à l'ItemList
     * @param l'Item à ajouter
     **/
    public void addItem( final Item pItem){
        this.aItems.put( pItem.getName(), pItem);
    }//addItem( final Item pItem)
    
    /**
     * Retire un Item de l'ItemList
     * @param String pItemName le nom de l'Item à retirer
     **/
    public void removeItem( final String pItemName){
        this.aItems.remove(pItemName);
    }//removeItem( final String pItemName)
    
    /**
     * Donne une chaine de caractère listant les objets Item de l'ItemListe par leurs noms et leurs poids
     * @return String la chaine de caractère listant les Items
     **/
    public String getString(){
        String vD = "";
        Collection<Item> vL = this.aItems.values();
        for( Item vI : vL ){
            vD = vD + "- " + vI.getName() + " : " + vI.getDescription() + " (poids : " + vI.getWeight()
            + " kg)\n";
        }
        return vD;
    }//getString()
    
    /**
     * Calcule le poids total des objets Item contenus dans l'ItemList
     * @return int le poids total calculé
     **/
    public int totalWeight(){
        int pI = 0;
        Collection<Item> vL = aItems.values();
        for( Item vI : vL ){
            pI += vI.getWeight();
        }
        return pI;
    }//totalWeight()
} // ItemList
