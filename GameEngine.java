import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003) DB edited (2019)
 */
public class GameEngine
{
    private Parser        aParser;
    private Player        aPlayer;
    private int           aTimer;
    private UserInterface aGui;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        this.aParser = new Parser();
        this.aPlayer = new Player();
        this.createRooms();
        this.aTimer = 40;
    }
    
    /**
     * Initialise l'attribut Userinterface de l'objet GameEngine et son attribut Player
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.aPlayer.setGUI(pUserInterface);
        this.printWelcome();
        if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
    }

    /**
     * Affiche les informations liées au lieux actuel
     */
    private void printLocationInfo(){
        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
    }//printLocationInfo()
    
    /**
     * Affiche le message de bienvenue
     */
    private void printWelcome()
    {
        this.aGui.println("Bienvenu dans Reach The Way Out!");
        this.aGui.println("Echappez vous de ce monde pour gagner la partie.");
        this.aGui.println("Ecrivez 'help' pour obtenir de l'aide.");
        this.aGui.println(" ");
        this.printLocationInfo();
    }//printWelcome()
    
    /**
     * Créer le réseaux de pièce
     */
    private void createRooms(){
        // Création des lieux
        Room vDepart = new Room("au point de départ", "images/depart.jpg");
        Room vBoss = new Room("à la sortie de ce monde, gardée par le monstre", "images/boss.jpg");
        Room vSoussol = new Room("au sous sol", "images/sous_sol.jfif");
        Room vTresor = new Room("dans la salle du trésor", "images/tresor.jfif");
        Room vBibliotheque = new Room("au niveau des grandes archives", "images/bibli.jfif");
        Room vVille = new Room("dans la ville, la où vivent la majorités des gens de ce monde", "images/ville.jfif");
        Room vPrison = new Room("à l'endroit où sont enfermés les opposants du régime", "images/prison.jfif");
        Room vCellule1 = new Room("dans la céllue du grand mage arcanique", "images/cellule1.jfif");
        Room vCellule2 = new Room("dans la cellule de Alaric, un noble ayant tenté de trahir le régime, il a été décapité ce matin, vous trouverez peut-être ses biens ici", "images/cellule2.jfif");
        Room vMarché = new Room("au marché de la ville, l'endroit où l'ont trouve tout ce que l'on désire", "images/marche.jfif");
        
        // NPC
        NPC vNPCInconnu = new NPC("Gandalf", "une personne banale, ignorez le", "-je suis muet.");
        NPC vNPCMarchand = new NPC("Isaak", "un marchand avec qui vous pouvez faire affaire", "Bienvenue dans ma boutique, je me ferais un plaisir de marchander avec vous.\nTapez vendre/acheter suivi du nom de l'objet que vous voulez me vendre/acheter.\nVoilà ce que j'ai pour vous :\n-pain (1 pieces d'or)\n-bouclier (20 pieces d'or)\n-épée (120 pièces d'or) ");
        NPC vNPCMage = new NPC("Merlin", "un mage emprisonné pour sa magie dangereuse", "Si vous comptez vous échapper de ce monde, il vous faudra une arme puissante.\nRamennez moi une épée et un parchemin (commande: échanger).");
        NPC vNPCBoss = new NPC("Sulyvahn", "un guerrier sanguinnaire chargé de proteger la sortie de ce monde", "Partez, ou combattez-moi. (commande : combattre)");
        
        vDepart.setNPC(vNPCInconnu);
        vMarché.setNPC(vNPCMarchand);
        vCellule1.setNPC(vNPCMage);
        vBoss.setNPC(vNPCBoss);
        
        // Objets
        Item vObjDepart = new Item("obj", "un objet de départ", 14);
        Item vDiamant = new Item("diamant", "un tresor d'une grande valeur", 10);
        Item vCookie = new Item("cookie", "un cookie magique", 1);
        Item vParchemin = new Item("parchemin", "un grimoire contenant de puissantes formules magiques", 1);
        Item vEmeraude = new Item("emeraude", "un trésor d'une grande valeur", 10);
        Beamer vBeamer = new Beamer();
        
        vDepart.addItem(vBeamer);
        vDepart.addItem(vObjDepart);
        vTresor.addItem(vDiamant);
        vSoussol.addItem(vCookie);
        vBibliotheque.addItem(vParchemin);
        vCellule2.addItem(vEmeraude);
        
        // Positions des sorties
        vDepart.setExit("nord", vBoss);
        vDepart.setExit("est", vVille);
        vDepart.setExit("bas", vSoussol);
        
        vBoss.setExit("sud", vDepart);
        
        //vSoussol.setExit("haut", vDepart); je l'ai retirée pour en faire une trapdoor
        vSoussol.setExit("est", vPrison);
        vSoussol.setExit("ouest", vTresor);
        
        vTresor.setExit("est", vSoussol);
        
        vBibliotheque.setExit("sud", vVille);
        
        vVille.setExit("nord", vBibliotheque);
        vVille.setExit("est", vMarché);
        vVille.setExit("bas", vPrison);
        vVille.setExit("ouest", vDepart);
        
        vPrison.setExit("haut", vVille);
        vPrison.setExit("est", vCellule2);
        vPrison.setExit("sud", vCellule1);
        vPrison.setExit("ouest", vSoussol);
        
        vCellule1.setExit("nord", vPrison);
        
        vCellule2.setExit("ouest", vPrison);
        
        vMarché.setExit("ouest", vVille);
        
        // Initialise le lieu courant
        this.aPlayer.setCurrentRoom(vDepart);
    }//createRooms()

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        this.aGui.println( "-----------------------------------------------------------------------------------\n> " + pCommandLine );//pour augmenter la lisibilité
        Command vCommand = this.aParser.getCommand( pCommandLine );

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "Je ne vois pas de quoi vous parlez..." );
            return;
        }
        
        String vCommandWord = vCommand.getCommandWord();
        if ( vCommandWord.equals( "aide" )){
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else
            this.printHelp();
        }
        else if ( vCommandWord.equals( "aller" ) ){
            this.goRoom( vCommand );
        }
        else if ( vCommandWord.equals( "test" ) ){
            if(!vCommand.hasSecondWord()){
                this.aGui.println( "tester quoi ? (court/parcoursideal/exploration)" );
            }
            else{
                this.lecture(vCommand.getSecondWord());
            }
        }
        else if ( vCommandWord.equals( "prendre" ) ){
            if(!vCommand.hasSecondWord()){
                this.aGui.println( "prendre quoi ? (entrez en 2nd mot: le nom de l'objet à ramasser)" );
            }
            else{
                this.aPlayer.take(vCommand.getSecondWord());
            }
        }
        else if ( vCommandWord.equals( "lacher" ) ){
            if(!vCommand.hasSecondWord()){
                this.aGui.println( "lacher quoi ? (entrez en 2nd mot: le nom de l'objet à lacher)" );
            }
            else{
                this.aPlayer.drop(vCommand.getSecondWord());
            }
        }
        else if ( vCommandWord.equals( "parler" ) ){
            if(!vCommand.hasSecondWord()){
                this.aGui.println( "parler à qui ? (entrez en 2nd mot: le nom de la personne)" );
            }
            else{
                this.talk(vCommand.getSecondWord());
            }
        }
        else if ( vCommandWord.equals( "vendre" ) ){
            if(!vCommand.hasSecondWord()){
                this.aGui.println( "vendre quoi ? (entrez en 2nd mot: le nom de l'objet que vous souhaitez vendre)" );
            }
            else{
                this.aPlayer.sell(vCommand.getSecondWord());
            }
        }
        else if ( vCommandWord.equals( "acheter" ) ){
            if(!vCommand.hasSecondWord()){
                this.aGui.println( "vendre quoi ? (entrez en 2nd mot: le nom de l'objet que vous souhaitez acheter)" );
            }
            else{
                this.aPlayer.buy(vCommand.getSecondWord());
            }
        }
        else if ( vCommandWord.equals( "manger" ) ){
            if(vCommand.hasSecondWord()){
                this.eat(vCommand.getSecondWord());
            }
            else
            this.eat();
        }
        else if ( vCommandWord.equals( "charger" ) ){
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else
            this.aPlayer.charge();
        }
        else if ( vCommandWord.equals( "tirer" ) ){
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else
            if(this.aPlayer.fire()){
                this.printLocationInfo();
                if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
            }
        }
        else if ( vCommandWord.equals( "regarder" ) ){
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else
            this.look();
        }
        else if ( vCommandWord.equals( "échanger" ) ){
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else
            this.aPlayer.trade();
        }
        else if ( vCommandWord.equals( "inventaire" ) ){
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else
            this.printInventoryInfo();
        }
        else if ( vCommandWord.equals( "retour" ) ){
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else
            this.goBack();
        }
        else if ( this.aPlayer.isNPCHere("Sulyvahn") && vCommandWord.equals( "combattre" )){
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else{
                if(this.aPlayer.fight()){
                    this.aGui.println("Vous avez battu le Sulyvahn et vous êtes échappé de ce monde. Bravo.");
                }
                else{
                    this.aGui.println("Sulyvahn vous a tué, vous avez perdu. ");
                }
                this.endGame();
            }
        }
        else if ( vCommandWord.equals( "quitter" ) ) {
            if(vCommand.hasSecondWord()){
                this.aGui.println( "pas besoin de second mot pour cette commande" );
            }
            else
                this.endGame();
        }
    }

    // implementations of user commands:

    public void goBack(){//public pour que UserInterface puisse l'utiliser
        if(this.aPlayer.getPreviousRooms().empty()){
            this.aGui.println("vous êtes déjà retournés en arrière jusqu'au départ!");
        }
        else{
            this.aPlayer.moveBackRoom();
            printLocationInfo();//printlocationinfo
            if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
    }
    
    /**
     * Affiche le message d'aide
     */
    private void printHelp()
    {
        this.aGui.println("Vous êtes perdu.");
        this.aGui.println("Vous flânez à travers l'université");
        this.aGui.println(" ");
        this.aGui.println("Les commandes sont:");
        this.aGui.println(aParser.getValidCommandsParser());
    }//printHelp()

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom( final Command pCommand ) 
    {
        if ( ! pCommand.hasSecondWord() ) {
            // if there is no second word, we don't know where to go...
            this.aGui.println( "aller où?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();

        // Try to leave current room.
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit( vDirection );

        if ( vNextRoom == null )
            this.aGui.println( "Il n'y a pas de porte!" );
        else {
            this.aPlayer.moveRoom(vNextRoom);
            this.printLocationInfo();
            this.decreaseTimer();
            if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
    }
    
    private void decreaseTimer(){
        this.aTimer --;
        this.aGui.println("temps restant: " + this.aTimer);
        if (this.aTimer == 0){
            this.aGui.println("time-out, you lost!");
            this.endGame();
        }
    }
    
    private void talk( final String pS){
        if(this.aPlayer.getCurrentRoom().getNPC().getName().equals(pS)){
            this.aGui.println(this.aPlayer.getCurrentRoom().getNPC().getDialogue());
        }
        else{
            this.aGui.println("Il n'y a personne portant ce nom dans cette salle. ");
        }
    }
    
    /**
     * affiche les informations liées à la location du joueur
     */
    private void look(){
        this.printLocationInfo();
    }
    
    
    /**
     * affiche un message après que le joueur ai mangé
     */
    private void eat(){
        this.aGui.println("tu as bien mangé, tu es rassasié...");
    }
    
    /**
     * Consommer le cookie, si la commande est bien tapée, sinon informe le joueur que la commande est mal tapée
     * @param String le deuxieme mot de la commande ( apres "eat" )
     */
    private void eat( final String pS){
        if (pS.equals("cookie")){
            this.aPlayer.cookie();
        }
        else{
            this.aGui.println("manger quoi?");
        }
    }
    
    /**
     * Affiche les informations liées à l'inventaire du joueur
     */
    private void printInventoryInfo(){
        this.aGui.println(this.aPlayer.getStringInventory());
    }
    
    /**
     * Lis un fichier de test
     * @param pString le nom du fichier
     */
    public void lecture( String pNomFichier){//source:"plus de technique"
        Scanner vSc;
        try { // pour "essayer" les instructions suivantes
            pNomFichier += ".txt";
            vSc = new Scanner( new File( pNomFichier ) );
            while ( vSc.hasNextLine() ) {
                String vLigne = vSc.nextLine();
                this.interpretCommand(vLigne);
            } // while
        } // try
        catch ( final FileNotFoundException pFNFE ) {
            this.aGui.println("Le fichier " + pNomFichier + " est introuvable");// traitement en cas d'exception
        } // catch
    }
    
    /**
     * Met fin au jeu et affiche un message de remerciement
     */
    private void endGame()
    {
        this.aGui.println( "Merci d'avoir jouer, A+." );
        this.aGui.enable( false );
    }

}
