# Illumio Coding Assessment

# Classes Description
  - **Application.java** - Main class to run the project. It takes csv file name as input and space seprated values of packets in following format [INBOUND TCP 40 192.168.1.1]
  - **Rules.java** - a model class which contains four parameters i.e. portStart, portEnd, ipStart, ipEnd. This stores a rule within which, if a valid IP falls. Also, I have converted the IP address to a long value (referred the code from here: https://gist.github.com/madan712/6651967)
  - **IPUtils.java** - Contains utilities methods to convert an IP address to a long value and other to check if an ip address lies between two valid ranges.
  - **Constants.java** - contains constants
  - **FirewallException.java** - custom exception to throw exceptions in the project
  - **Firewall.java** - contains four arraylists i.e inbound UDP, outbound UDP, inbound TCP and outbound TCP of rules. These lists are sorted by startPort number. Whenever we need to check if a packet should be accepted, we first find an index in one of these list, based on the packet type using binary search based on portStart and then make our search linear from there on. This will lead to a major improve in the performance of the code.

# Testing
  - I have not done any performance testing, but I have done the functional testing and I have written junit tests as well.

# Some cool facts
  - I have created four different lists based on direction and protocol and sorted them by port start number. This sorting helps when we have to determine if a packet needs to be accepted as I have implemented binary search based on startPort number of the packet. Also, using four different lists will save some time as well.
  - Insted of storing IP address as string and then comparing, I have converted IP addresses to long values as it makes them easier and faster to compare than string values.

# Interested to work with
 1. Platform team
 2. Data Team
