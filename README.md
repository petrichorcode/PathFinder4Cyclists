# Path Finder for cyclists in London

This project is an ongoing development to finding the cleanest (pollution-wise) and safest path for cyclists in London.

# How?
- Taking data from sensors and creating a dispersion model of the city. 
- Creating a Road Type hierarchy system that is tailored to the cyclist, thus routing them through roads which are most appropriate. 
- Taking a calculated approach to providing the safest, healthiest, and most efficient route

# Current Progress
- A Search for a path can be made from the terminal using Node, Geolocation or address/POI.
- AStar and Binary Heap work well in conjunction

# Coming Soon
- Provide Trip data (Distance/Estimated time) along with the route 
- Write lots of tests

# Current issues/Areas for improvement
- The dispersion modelling currently does not exist, however pollution data is being retrieved and applied to the algorithms. 
- The current adjacency list implementation is as a good solution to early development but needs to be improved and have its own class.
- The Server/Client side is somewhat built, but needs some more technical prowess to get it working
