package statelessservice;

import java.util.concurrent.CompletableFuture;
import java.util.List;

import microsoft.servicefabric.services.communication.runtime.ServiceInstanceListener;
import microsoft.servicefabric.services.runtime.StatelessService;	

public class HelloWorldService extends StatelessService {

    @Override
    protected List<ServiceInstanceListener> createServiceInstanceListeners() {
        // TODO: If your service needs to handle user requests, return the list of ServiceInstanceListeners from here.
        return super.createServiceInstanceListeners();
    }

   // @Override
   // protected CompletableFuture<?> runAsync() {
        // TODO: Replace the following with your own logic.
     //   return super.runAsync();
   // }
    
    public static void main(String[] args) throws Exception {
        try {
            ServiceRuntime.registerStatelessServiceAsync("HelloWorldType", (context) -> new HelloWorldService(), Duration.ofSeconds(10));
            logger.log(Level.INFO, "Registered stateless service type HelloWorldType.");
            Thread.sleep(Long.MAX_VALUE);
        } 
        catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception in registration: {0}", ex.toString());
            throw ex;
        }
    }


@Override
protected CompletableFuture<?> runAsync() {

    CompletableFuture<?> completableFuture = new CompletableFuture<>();
    ExecutorService service = Executors.newFixedThreadPool(1);

    Future<?> userTask = service.submit(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            try
            {
               logger.log(Level.INFO, this.context().serviceName().toString());
               Thread.sleep(1000);
            }
            catch (InterruptedException ex)
            {
                logger.log(Level.INFO, this.context().serviceName().toString() + " interrupted. Exiting");
                return;
            }
        }
     });

    completableFuture.handle((r, ex) -> {
        if (ex instanceof CancellationException) {
            userTask.cancel(true);
            service.shutdown();
        }
        return null;
    });

    return completableFuture;
}
}