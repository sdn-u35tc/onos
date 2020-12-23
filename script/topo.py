from mininet.topo import Topo


class MyTopo(Topo):

    def __init__(self):
        Topo.__init__(self)
        hosts = [0]
        switches = [0]
        for i in range(1, 34):
            hosts.append(self.addHost("h" + str(i), ip="10.0.0." + str(i)))
        for i in range(1, 26):
            switches.append(self.addSwitch("s" + str(i)))

        self.addLink(switches[1], hosts[1])
        self.addLink(switches[15], hosts[2])
        self.addLink(switches[23], hosts[3])
        self.addLink(switches[3], hosts[4])
        self.addLink(switches[24], hosts[5])
        self.addLink(switches[15], hosts[6])
        self.addLink(switches[7], hosts[7])
        self.addLink(switches[7], hosts[8])
        self.addLink(switches[8], hosts[9])
        self.addLink(switches[4], hosts[10])
        self.addLink(switches[11], hosts[11])
        self.addLink(switches[9], hosts[12])
        self.addLink(switches[12], hosts[13])
        self.addLink(switches[17], hosts[14])
        self.addLink(switches[25], hosts[15])
        self.addLink(switches[21], hosts[16])
        self.addLink(switches[21], hosts[17])
        self.addLink(switches[21], hosts[18])
        self.addLink(switches[19], hosts[19])
        self.addLink(switches[19], hosts[20])
        self.addLink(switches[22], hosts[21])
        self.addLink(switches[22], hosts[22])
        self.addLink(switches[23], hosts[23])
        self.addLink(switches[23], hosts[24])
        self.addLink(switches[20], hosts[25])
        self.addLink(switches[20], hosts[26])
        self.addLink(switches[18], hosts[27])
        self.addLink(switches[16], hosts[28])
        self.addLink(switches[16], hosts[29])
        self.addLink(switches[12], hosts[30])
        self.addLink(switches[5], hosts[31])
        self.addLink(switches[5], hosts[32])
        self.addLink(switches[14], hosts[33])

        self.addLink(switches[1], switches[9])
        self.addLink(switches[1], switches[6])
        self.addLink(switches[2], switches[11])
        self.addLink(switches[2], switches[17])
        self.addLink(switches[2], switches[20])
        self.addLink(switches[2], switches[5])
        self.addLink(switches[3], switches[16])
        self.addLink(switches[3], switches[13])     
        self.addLink(switches[4], switches[7])
        self.addLink(switches[4], switches[8])
        self.addLink(switches[4], switches[10])
        self.addLink(switches[4], switches[11])
        self.addLink(switches[4], switches[9])
        self.addLink(switches[5], switches[6])
        self.addLink(switches[5], switches[13])
        self.addLink(switches[6], switches[14])
        self.addLink(switches[6], switches[15])
        self.addLink(switches[6], switches[12])
        # self.addLink(switches[7], switches[])
        # self.addLink(switches[8], switches[])
        self.addLink(switches[9], switches[12])
        self.addLink(switches[10], switches[11])
        self.addLink(switches[10], switches[17])
        self.addLink(switches[11], switches[25])
        self.addLink(switches[12], switches[16])
        self.addLink(switches[12], switches[25])
        # self.addLink(switches[13], switches[])
        # self.addLink(switches[14], switches[])
        # self.addLink(switches[15], switches[])
        # self.addLink(switches[16], switches[])
        self.addLink(switches[17], switches[25])
        self.addLink(switches[17], switches[18])
        self.addLink(switches[17], switches[21])
        self.addLink(switches[18], switches[19])
        self.addLink(switches[19], switches[21])
        self.addLink(switches[20], switches[22])
        self.addLink(switches[20], switches[23])
        self.addLink(switches[21], switches[25])
        # self.addLink(switches[22], switches[])
        # self.addLink(switches[23], switches[])
        self.addLink(switches[24], switches[25])
        # self.addLink(switches[25], switches[])


topos = {'mytopo': (lambda: MyTopo())}