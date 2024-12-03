module PerfumeManager {
    // Export packages that need to be accessible to clients or the RMI framework
    exports service;
    exports gui;
  //  exports serilize;

    // Require the java.rmi module for RMI functionality
    requires java.rmi;
	requires java.desktop;

    // Open packages to allow serialization and reflection access by RMI
    opens service to java.rmi;
}
