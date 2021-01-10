package com.github.studeasy.gui.routers;

public class CommandOfServiceRouter extends AbstractRouter{
    /**
     * Singleton of the CommandOfService router
     */
    private static CommandOfServiceRouter commandOfServiceRouter = null;

    /**
     * Path to the view all demande to the service
     */
    public final static String VIEW_ALL_DEMANDE_SERVICE_FXML_PATH = "views/commandService/buyService.fxml";

    /**
     * Path to the page historic command
     */

    public final static String SERVICES_BOUGHT_FXML_PATH = "views/commandService/HistoricCommand.fxml";

    /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private CommandOfServiceRouter(){
        super();
    }

    /**
     * Used to return the unique instance of the CommandOfServiceRouter
     * @return commandOfServiceRouter
     */
    public static CommandOfServiceRouter getInstance(){
        if(commandOfServiceRouter == null){
            commandOfServiceRouter = new CommandOfServiceRouter();
        }
        return commandOfServiceRouter;
    }
}
