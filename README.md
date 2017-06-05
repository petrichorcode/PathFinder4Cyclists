# Path Finder for cyclists in London

This project is an ongoing development to finding the cleanest (pollution-wise) and safest path for cyclists in London. The scope of this project goes beyond a final university project - developments made after 1/06/2017 are for reasons other than academic pursuits.


# How?
- Taking data from sensors and creating a dispersion model of the city. 
- Creating a Road Type hierarchy system that is tailored to the cyclist, thus routing them through roads which are most appropriate. 
- Taking a calculated approach to providing the safest, healthiest, and most efficient route

# Current Progress
- Flaw in logic about map construction discovered after looking deeper into the construct of Way elements. This has resulted in some over-engineering in the RoadNetwork class that will require plenty of refactoring.
- A Search for a path can be made from the terminal using Node, Geolocation or address/POI.
- AStar and Binary Heap work well in conjunction

# Coming Soon
- An improved adjacency list that will provide more accurate results (perhaps with one-way street implemented)
- Provide Trip data (Distance/Estimated time) along with the route 
- Write lots of tests

# Current issues/Areas for improvement
- The dispersion modelling currently does not exist, however pollution data is being retrieved and applied to the algorithms. 
- Development of the adjacency list and implementing one-way streets
- The Server/Client side is somewhat built, but needs some more technical prowess to get it working
