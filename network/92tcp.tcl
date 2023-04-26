#Create a simulator object
set ns [new Simulator]

#Define different colors for data flows (for NAM)
$ns color 1 Blue

#Open the NAM trace file
set nf [open 92tcp.nam w]
$ns namtrace-all $nf

set np [open 92tcp.tr w]
$ns trace-all $np

#Define a 'finish' procedure
proc finish {} {
        global ns nf np
        $ns flush-trace
        #Close the NAM trace file
        close $nf
        #Execute NAM on the trace file        
	exec nam 92tcp.nam &
        exit 0
}

#Create four nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

#Create links between the nodes
$ns duplex-link $n0 $n2 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 2Mb 10ms DropTail
$ns duplex-link $n2 $n3 1.7Mb 20ms DropTail

#Set Queue Size of link (n2-n3) to 10
$ns queue-limit $n2 $n3 10

#Give node position (for NAM)
$ns duplex-link-op $n0 $n2 orient right-down
$ns duplex-link-op $n1 $n2 orient right-up
$ns duplex-link-op $n2 $n3 orient right

#Monitor the queue for link (n2-n3). (for NAM)
$ns duplex-link-op $n2 $n3 queuePos 0.5


#Setup a TCP connection
set tcp [new Agent/TCP]
$tcp set class_ 2
$ns attach-agent $n0 $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $n3 $sink
$ns connect $tcp $sink
$tcp set fid_ 2

#Setup a FTP over TCP connection
set ftp [new Application/FTP]
$ftp attach-agent $tcp


# setting packet size 
$ftp set packet_size_ 1500

#setting bit rate
$ftp set rate_ 1mb

# setting random false means no noise
$ftp set random_ false


#Schedule events for the CBR and FTP agents
$ns at 0.1 "$ftp start"
$ns at 3.0 "$ftp stop"

#Detach tcp and sink agents (not really necessary)
$ns at 3.5 "$ns detach-agent $n0 $tcp ; $ns detach-agent $n3 $sink"

#Call the finish procedure after 5 seconds of simulation time
$ns at 4.0 "finish"

#Print CBR packet size and interval
puts "FTP packet size = [$ftp set packet_size_]"
#puts "FTP interval = [$ftp set interval_]"

#Run the simulation
$ns run

