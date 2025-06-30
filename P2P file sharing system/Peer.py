import threading
import socket
from Peer_info import PeerINFO
import os
import time
import sys
import timeit
from random import randint, choice
from time import sleep
from partition import * #our modules for handling the fragmenation and re-construction of file fragements
import traceback

############################### MISC FUNCS##############################
#misc functions
def parse_fragment_dict(s):
    s= s.strip("{")
    s= s.strip("}")
    #list of key value pairs as strings
    pairs = s.split('), ')

    # Initialize an empty dictionary
    result = {}

    # Iterate over key-value pairs
    for pair in pairs:
        token_id , value_str = pair.split(": ({")
        # Convert key to integer
        token_id = int(token_id.strip())

        # Parse value
       
        value_parts = value_str.split("},")
        frag_list = value_parts[0].split(", ")
        frag_list = [frag.strip("'") for frag in frag_list]
        strbool = ""
        #print(value_parts[1].strip())
        if "True" in value_parts[1].strip():
            strbool = "true"
        value_bool = bool(strbool)
        value_set = set(frag_list)
        # Add key-value pair to dictionary
        result[token_id] = (value_set, value_bool)
    
    return result

########################################################################
disconnect_server_flag = None
class Peer:
    def __init__(self, i ) -> None:
        self.session_id = -1 # this means it has not yet connected to the peer
        self.IP = "127.0.0.1" #allow the OS to assign my IP
        self.port = "999"+ str(i) # this is the port The peer accepts server connections to stream files to other peers
        self.username = None
        self.password = None
        self.count_downloads = 0 
        self.count_failures = 0 
        self.secret= randint(0, 100) #used to close server
        self.serverHOST = "127.0.0.1"  # The server's hostname or IP address
        self.serverPORT = 65432  # The port used by the server
        self.shared_folder = "shared_directory/peer"+str(i) # the directory where the peer stores the files it is willing to share 

        self.file_seeder_frags_lookup = partition_allfiles(self.shared_folder) #has the number of fragments of each file we are the seeder of 
        # a dictionary that contains the complete files (files which this Peer instance is the seeder of) and the value is the number of pieces/fragments

        # NOTE (look at notes )make a data structure that keeps track of the fragements given by each peer for each file 
        self.seeder_waitlist = []
        self.fulfil_seeder_serve = False
        self.response_seeder_serve_event = threading.Event()
        self.Seeder_serve_lock = threading.Lock() 
        self.selectedSeederPort = None
        self.collab_waitdict = dict()
        self.fulfil_collab = False
        self.response_collab_event = threading.Event()
        self.collab_lock = threading.Lock()
        self.selectedCollabPeerPort = None
        self.recvFrom = dict()#peer: (posa file mou exei steieli)
    
    def register(self , username , password):
        """
        This method a suggested username the user has prompted 
        and sends it with his selected password to register to the tracker
        """
        #Connect to the server 
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((self.serverHOST, self.serverPORT))

            request = f"REGISTER {username} {password} "
            s.sendall(request.encode('utf-8'))
            response = s.recv(1024).decode('utf-8')

            if response == "SUCCESS":
                self.username = username
                self.password = password
                print("Registration successful")
               
                return True
            else:
                print(f"Registration failed: {response}")
                
                return False
            
    def login(self, username , password):
        #NOTE : not tested and not completely implemented
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((self.serverHOST, self.serverPORT))

            request = f"LOGIN {username} {password} {self.IP} {self.port}"
            s.sendall(request.encode('utf-8'))
            response = s.recv(1024).decode('utf-8')
            if response.split()[0] == "SUCCESS":
               
                print(f"Assigned token_id: {response.split()[1]}")
                
                token=  response.split()[1]
                self.session_id = token # implement parse integer 
                self.inform()
                threading.Thread(target=self.server_action).start()

                return True
            else:
                #self.session_id= response
                print(f"Login failed: {response}")
                return False
            
    def inform(self):
        # Connect to the tracker using the existing socket connection
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((self.serverHOST, self.serverPORT))

            # Construct the message to send to the tracker
            message = f"INFORM {self.session_id}"

            #Get a list of all the files in the shared directory
            files_in_shared_dir = os.listdir(os.getcwd() + "/" + self.shared_folder)
            print(files_in_shared_dir)
            files_in_shared_dir = [filename for filename in files_in_shared_dir if filename.endswith(".txt")]
            frags =""
            print(self.file_seeder_frags_lookup)
            for file in files_in_shared_dir:
                for i in range(1, self.file_seeder_frags_lookup[file] + 1):
                    if i == self.file_seeder_frags_lookup[file]:
                        frags+=str(i) 
                    else:
                        frags+= (str(i) + "-")
                message += f" {file} {frags}"
                frags= ""
            print(message)

            # Send the message to the tracker
            s.sendall(message.encode('utf-8'))

            # Wait for the tracker's response and handle it accordingly
            response = s.recv(1024).decode('utf-8')
            if response.startswith("SUCCESS"):
                print("\nPeer informed the tracker about its shared files successfully.")
            else:
                print(f"Error informing the tracker: {response}")
    def list(self)-> list:
        # Connect to the tracker using the existing socket connection
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((self.serverHOST, self.serverPORT))

            # Construct the message to send to the tracker
            message = f"LIST {self.session_id}"

            # Send the message to the tracker
            s.sendall(message.encode('utf-8'))

            response = s.recv(1024).decode('utf-8')
            available_files = response.split()

            if len(available_files) == 0:
                print("No files available.")
            else :
                print("Available files:")
                for file in available_files:
                    print(file)
            return available_files

    def details(self, filename): 
    # Connect to the tracker using the existing socket connection
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((self.serverHOST, self.serverPORT))
            message = f"DETAILS {self.session_id} {filename}"
            s.sendall(message.encode('utf-8'))
            response = s.recv(1024).decode('utf-8')
            #print(response)
            #print("====================================================================")
            response_fragments = {}
            if response.strip() != "FILE DOES NOT EXIST" :

                response_fragments = parse_fragment_dict(response.split("???")[1])
                peers_info = response[response.find("[")+1:response.find("]")].split("|")  #NEW
                peers = []
                for peer_info in peers_info:
                    peer = PeerINFO.deserialize(peer_info)
                    peers.append(peer)
                #print(response_fragments)
                #
                #for i in peers:
                    #print(i)
                return peers , response_fragments
            else:
                print(f"Error retrieving the details of the file: {response}")
                return [] , response_fragments
    def logout(self):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((self.serverHOST, self.serverPORT))
            message = f"LOGOUT {self.session_id}"
            s.sendall(message.encode('utf-8'))
            response = s.recv(1024).decode('utf-8')
            self.close_server()

            return response
    def close_server(self):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        
            s.connect((self.IP , int(self.port)))
            message = f"CLOSE {str(self.secret)}"
            s.sendall(message.encode('utf-8'))
            response = s.recv(1024).decode('utf-8')
            print(response)
    def CheckActive(self , peerIP , peerport, id):
        try:
            with socket.socket(socket.AF_INET , socket.SOCK_STREAM) as s:
                s.connect((peerIP , int(peerport)))
                #print("kanw connect me peer")
                # NOTE Set a timeout of 5 seconds for the socket connection
                s.settimeout(500)
                rtt = float('inf')
                message = f"CHECK_ACTIVE"
                start_time = timeit.default_timer()  # Record the start time before sending the message

                s.sendall(message.encode('utf-8'))

                response = s.recv(1024).decode('utf-8')
                end_time = timeit.default_timer()  # Record the end time after receiving the response
                rtt = end_time - start_time  # Calculate the Round Trip Time
                return response[0] , rtt*1000
        except socket.timeout:
            return "Connection timed out" , rtt
        except ConnectionRefusedError:
            with socket.socket(socket.AF_INET , socket.SOCK_STREAM) as s:
                s.connect((self.serverHOST, self.serverPORT))
                message = f"DISCONNECT_PEER {id}"
                s.sendall(message.encode('utf-8'))
                response = s.recv(1024).decode('utf-8')
            
            return f"Peer is not reachable. {response}" , rtt
        except Exception as e:
            return f"An error occurred: {e}" 
        finally:
            s.close()
    def evaluate_peers(self, peers , rtts): # peer[i] took rtts[i] ms to respond
        score_list = []
        for i in range(0,len(peers)):
            score_list.append(rtts[i] *(0.75 * peers[i].count_downloads + 1.25*peers[i].count_failures))
        return score_list
    
    def Download(self, filename):
        try:#gyrnaei o tracker posa fragments exei sto synolo to file filename
            with socket.socket(socket.AF_INET , socket.SOCK_STREAM) as s:
                s.connect((self.serverHOST, self.serverPORT))
                message = f"TOTAL_FRAGMENTS {filename}"
                #print(f"esteila {message}")
                s.sendall(message.encode('utf-8'))
                response = s.recv(1024).decode('utf-8')
                totalFragNumber = int(response)
        except Exception as e:
            return f"An error occured: {e}"
        
        files_in_shared_dir = os.listdir(os.getcwd() + "/" + self.shared_folder)#ola ta files pou anoikoun ston peer
        downloaded_fragments = {}#downloaded fragments toy file filename
        for i in range(1, totalFragNumber + 1):
            downloaded_fragments[i] = False
        for i in range(1, totalFragNumber+1):
            name = filename.removesuffix('.txt') + "-" + str(i) + ".fragments"
            for f in files_in_shared_dir:
                #print(f)
                if f == name:
                    downloaded_fragments[i] = True
        
        list_of_wanted_fragments = list()#fragments gia download
        list_of_downloaded_fragments = list()#fragments pou exei

        for key in downloaded_fragments:
            if downloaded_fragments[key] == False:
                list_of_wanted_fragments.append(key)#to gemizei me osa den exei downloaded
            else: 
                list_of_downloaded_fragments.append(key)
        while len(list_of_wanted_fragments):
        #for x in range(9):#auto gia debugging
            peers, fragments = self.details(filename)
            evaluations = list()
            if len(peers) == 0:
                return f"FILE {filename} NON EXISTENT"
            
            for p in peers:
                if p.token_id == self.session_id:
                    evaluations.append(float('inf'))
                    continue
                #print("kanw checkactive")
                checkActiveResponse = self.CheckActive(p.ip, p.port, p.token_id)
                if checkActiveResponse[0].__contains__("POSITIVE_ACK"):
                    evaluations.append(checkActiveResponse[1])
                else: 
                    evaluations.append(float('inf'))
            peers_score_list = self.evaluate_peers(peers, evaluations)
            for i in range(1, len(peers)):
                key = peers_score_list[i]
                peer = peers[i]
                j = i - 1
                while j >= 0 and key < peers_score_list[j]:
                    peers[j + 1] = peers[j]
                    peers_score_list[j + 1] = peers_score_list[j]
                    j -= 1
                peers[j + 1] = peer
                peers_score_list[j + 1] = key
            
            for p in peers:
                if p.token_id == self.session_id:
                    continue
                current_peer = p.token_id
                isSeeder = False
                for key in fragments:
                    if str(key) == str(current_peer):
                        isSeeder = fragments[key][1]
                #print(p)
                #print("-------------------------------------------------------!!!!!!!!!!!!!!!!!!!!")
                #print(isSeeder)
                #print("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]")
                check = self.Select(list_of_wanted_fragments, filename, isSeeder, p, list_of_downloaded_fragments)
                if check:
                    if not p in self.recvFrom.keys():
                        self.recvFrom[p] = 1
                    else:
                        new_number_of_files = self.recvFrom[p] + 1
                        self.recvFrom[p] = new_number_of_files
                    print(check)
                    break



    def Select(self, list, filename, isSeeder, peer, list_of_downloaded_fragments):
        frag_number = choice(list)
        fragment = filename.removesuffix('.txt') + '-' + str(frag_number) + ".fragments"
        return self.CollaborativeDownload(fragment, isSeeder, peer, list, list_of_downloaded_fragments)

    def CollaborativeDownload(self, fragment, isSeeder, peer, lista, downloaded_fragments):
        try:
            with socket.socket(socket.AF_INET , socket.SOCK_STREAM) as s:
                #print(isSeeder)
                #print("---------------------------------------------------")
                if isSeeder:
                    s.connect((peer.ip, int(peer.port)))
                    #print("kanw connect me peer")
                    message = f"SEEDER_DOWNLOAD {fragment}"
                    s.sendall(message.encode('utf-8'))
                    filepath = self.shared_folder + "/" + fragment
                    with open(filepath, 'wb') as file:
                        while True:
                            chunk = s.recv(1024)
                            if "end" in chunk.decode():
                                break
                            if chunk.decode().startswith("UNAVAILABLE_FILE"):
                                print("unavailable")
                                break
                            if chunk.decode().__contains__("not able"):
                                print("not able to send")
                                break
                            file.write(chunk)
                    #print("success")
                    toRemove = fragment.removesuffix('.fragments') 
                    #print(toRemove)
                    file_split = toRemove.split('-')
                    #print(file_split)
                    fragToRemove = int(file_split[1])
                    lista.remove(fragToRemove)
                    filename = file_split[0]
                    success = self.notify(peer.token_id, True, filename, self.session_id, fragToRemove)
                    return success
                else:
                    s.connect((peer.ip, int(peer.port)))
                    #print(fragment)
                    #print("-+_+_+_+_+_++_+__+_++_+__")
                    message = f"COLLABORATIVE_DOWNLOAD {fragment} {self.session_id}"
                    s.sendall(message.encode('utf-8'))
                    filepath = self.shared_folder + "/" + fragment
                    with open(filepath, 'wb') as file:
                        #print("anoi3a file")
                        while True:

                            chunk = s.recv(1024)
                            check_str = chunk.decode()
                            #print(check_str)

                            if "end" in check_str:
                                s.send(str("end").encode())
                                break
                            if check_str.startswith("UNAVAILABLE_FILE"):
                                print("unavailable")
                                file.close()
                                os.remove(filepath)
                                break
                            if "Peer_was_not" in check_str:
                                print("peer was not available")
                                file.close()
                                os.remove(filepath)
                                break
                            if "REQUEST" in check_str:
                                    fragmentsToChoose = chunk.decode('utf-8').split()[1:]
                                    #print(fragmentsToChoose)
                                    string_frags_to_chose = ""
                                    for i in fragmentsToChoose:
                                        string_frags_to_chose += i
                                    x = string_frags_to_chose.removeprefix('[')
                                    y = x.removesuffix(']')
                                    str_list = y.split(',')
                                    final_list = list()
                                    for i in str_list:
                                        final_list.append(int(i))
                                    #print(final_list)
                                    #print(downloaded_fragments)
                                    fragChoicesToSend = list()
                                    for key in downloaded_fragments:
                                        #print(key)
                                        if key in final_list:
                                            fragChoicesToSend.append(key) 
                                    if not fragChoicesToSend:
                                        print("den exw fragments")
                                        s.sendall(str("I DONT_OWN ANY OF THE WANTED FRAGMENTS").encode('utf-8'))
                                        break
                                    fragmentSend = choice(fragChoicesToSend)
                                    #print(fragmentSend)
                                    filename = fragment.split('-')[0]
                                    file_pathSend = self.shared_folder + "/" + filename + "-" + str(fragmentSend) + ".fragments"
                                    #print(file_pathSend)
                                    if os.path.exists(file_pathSend):
                                        outFrag = filename.removesuffix('.txt') + "-" + str(fragmentSend) + ".fragments"
                                        s.sendall(outFrag.encode('utf-8'))
                                        #print(f"esteila {outFrag}")
                                        incoming = s.recv(1024).decode('utf-8')
                                        if "continue" in incoming:
                                            with open(file_pathSend, 'rb') as file2:
                                                data = file2.read()
                                                while data:
                                                    s.send(data)
                                                    #print("esteila data")
                                                    data = file2.read()
                                                    if not data:
                                                        s.send(str("end").encode())
                                                        #print("esteial end")
                                                        break
                                                    print("file sent")
                                    else:
                                        s.send(str("DONT_OWN").encode('utf-8'))
                            #print("grafw sto file")        
                            file.write(chunk)
                    #print("success------------------------------------------------------------------------------")
                    toRemove = fragment.removesuffix('.fragments') 
                    file_split = toRemove.split('-')
                    fragToRemove = int(file_split[1])
                    lista.remove(fragToRemove)
                    return True
        except Exception as e:
            raise e
        
     

    def notify(self , peer_token, success_flag, filename, receivers_id, fragment=0):
        #print("o peer ekane notify")
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((self.serverHOST, self.serverPORT))
            message = f"NOTIFY {peer_token} {success_flag} {filename} {receivers_id} {fragment}"
            #print(f"esteila {message}")
            s.sendall(message.encode('utf-8'))
            response = s.recv(1024).decode('utf-8')
            #print(f"dexthka {response}!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            #if response contains message that we have every fragment of the file(filename call assemble)
            if(response.__contains__("SUCCESS")):
                #print("mphke sto success")
                #print(response.split()[3])
                if(response.split()[3] == "True"):#to [3] einai to flag gia to an einai seeder h oxi
                    absolutePath = os.path.dirname(__file__)
                    frag_dir = os.path.join(absolutePath, self.shared_folder)
                    fragnum = int(response.split()[2])
                    assemble(frag_dir, fragnum, response.split()[1])#to [2] einai ta fragments pou apoteloun ton fakelo kai to [1] to filename
                return f"FRAGMENT {response.split()[4]} OF FILE {response.split()[1]} WAS DOWNLOADED SUCCESSFULLY"
            else:
                #print("mphke sto fail")
                return "DOWNLOAD HAS FAILED. TRYING AGAIN."    


            


    def server_action(self):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_socket:
            server_socket.bind((self.IP, int(self.port)))
            server_socket.listen()
            
            #Method close server after logout
            while True:
                client_socket, client_address = server_socket.accept()
                self.server_sock = client_socket
                print(f"New connection from {client_address}")
                thread = threading.Thread(target=self.handle_peer_conn, args=(client_socket,client_address, server_socket ))
                thread.start()

                if disconnect_server_flag:
                    server_socket.close()
                    break                
                

    def handle_peer_conn(self, client_socket , client_address, server_socket):
        global disconnect_server_flag
        while True:
            try:
                request = client_socket.recv(1024).decode('utf-8')
                if not request:
                    break
                # Parse the request and execute the corresponding action
                if request.startswith("CHECK_ACTIVE"):
                    response = f"POSITIVE_ACK {self.session_id}"
                    #break 
                    #NOTE remove '#' to simulate check Active failure
                    client_socket.sendall(response.encode('utf-8'))
                    break
                
                elif request.startswith("CLOSE"):
                    if self.secret == int(request.split()[1]):
                        response = "OK"
                        client_socket.sendall(response.encode('utf-8'))
                        disconnect_server_flag = True
                        
                    else:
                        response = "DENIED"
                        client_socket.sendall(response.encode('utf-8'))

                elif request.startswith("COLLABORATIVE_DOWNLOAD"):
                    self.collab_lock.acquire()
                    self.collab_waitdict[int(request.split()[2])] = (client_socket, client_address)
                    self.collab_lock.release()
                    if not self.fulfil_collab:
                        self.collab_lock.acquire()
                        self.fulfil_collab = True
                        self.collab_lock.release()
                        threading.Timer(0.2, self.select_client).start()
                    self.response_collab_event.wait()
                    try:
                        #print("synexizw")
                        #print(self.selectedCollabPeerPort)
                        #print(client_address[1])
                        if self.selectedCollabPeerPort == client_address[1]:
                            files_in_shared_dir = os.listdir(os.getcwd() + "/" + self.shared_folder)#ola ta files pou anoikoun ston peer
                            downloaded_fragments = {}#downloaded fragments toy file filename
                            filename = request.split()[1].split('-')[0] + ".txt"
                            #print(filename)
                            try:#gyrnaei o tracker posa fragments exei sto synolo to file filename
                                with socket.socket(socket.AF_INET , socket.SOCK_STREAM) as s:
                                    s.connect((self.serverHOST, self.serverPORT))
                                    message = f"TOTAL_FRAGMENTS {filename}"
                                    #print(f"esteila {message}")
                                    s.sendall(message.encode('utf-8'))
                                    response = s.recv(1024).decode('utf-8')
                                    totalFragNumber = int(response)
                            except Exception as e:
                                return f"An error occured: {e}"
                            
                            for i in range(1, totalFragNumber + 1):
                                downloaded_fragments[i] = False

                            for i in range(1, totalFragNumber+1):
                                name = filename.removesuffix('.txt') + "-" + str(i) + ".fragments"
                                #print(name)
                                for f in files_in_shared_dir:
                                    if f == name:
                                        downloaded_fragments[i] = True
                                        break

                            list_of_wanted_fragments = list()
                            for key in downloaded_fragments:
                                if downloaded_fragments[key] == False:
                                    list_of_wanted_fragments.append(key)
                            #print(list_of_wanted_fragments)
                            fileFragmentToSend = request.split()[1]
                            file_path = self.shared_folder + "/" + fileFragmentToSend
                            #print(file_path)
                            if os.path.exists(file_path):
                                with open(file_path, 'rb') as file:
                                    data = file.read()
                                    while data:
                                        client_socket.send(data)
                                        #print("esteila")
                                        data = file.read()
                                        if not data:
                                            client_socket.send(str(f"REQUEST {list_of_wanted_fragments}").encode())
                                            #print("esteila to request")
                                            incoming_frag_name = client_socket.recv(1024).decode('utf-8')
                                            #print(incoming_frag_name)
                                            if "DONT_OWN" in incoming_frag_name:
                                                #print(incoming_frag_name)
                                                client_socket.send(str("end").encode())
                                                print("kleinw")
                                                break
                                            with open((self.shared_folder + "/" + incoming_frag_name), 'wb') as file2:
                                                message = "continue"
                                                client_socket.sendall(message.encode('utf-8'))
                                                #print(f"esteila {message}")
                                                while True:
                                                    chunk = client_socket.recv(1024)
                                                    if "end" in chunk.decode():
                                                        print("kleinw")
                                                        break
                                                    file2.write(chunk)
                                            client_socket.send(str("end").encode())
                                            #print("esteila end")
                                            #print("sent file")
                                            break
                            else:
                                response = "UNAVAILABLE_FILE"
                                client_socket.sendall(response.encode('utf-8')) 
                        else:
                            print("peer not available")
                            response = "Peer_was_not able to send the file fragment"
                            client_socket.sendall(response.encode('utf-8'))
                            #print(f"esteila {response}")
                    finally:
                        client_socket.close()
                        self.collab_lock.acquire()
                        self.selectedCollabPeerPort = None
                        if request.split()[2] in list(self.collab_waitdict.keys()):
                            self.collab_waitdict.pop(request.split()[2])
                        self.collab_lock.release()  
                    

                elif request.startswith("SEEDER_DOWNLOAD"):
                    self.Seeder_serve_lock.acquire()
                    self.seeder_waitlist.append((client_socket, client_address))
                    self.Seeder_serve_lock.release()
                    #set the global timer flag 
                    if not self.fulfil_seeder_serve:
                        self.Seeder_serve_lock.acquire()
                        self.fulfil_seeder_serve=True 
                        self.Seeder_serve_lock.release()
                        threading.Timer(0.2, self.select_clients_response).start()
                    
                    #wait until the event is completed
                    self.response_seeder_serve_event.wait()
                    #Event has been completed so respond to each Client accordingly 
                    try:
                        if self.selectedSeederPort[1] ==  client_address[1]:
                            print("epilex8hke to port " + str(client_address[1]))
                            #response with a random fragment
                            fileFragmentToSend = request.split()[1]
                            file_path = self.shared_folder + "/" + fileFragmentToSend
                            #print(file_path)
                            if os.path.exists(file_path):
                                with open(file_path, 'rb') as file:
                                    data = file.read()
                                    while data:
                                        #print("data----------------------------------------------------------------")
                                        client_socket.send(data)
                                        data = file.read()
                                        if not data:
                                            client_socket.send(str("end").encode())
                                            break
                                            #break
                                        #print("FILE SENT*******************************************************************************************")
                            
                            
                            else:
                                response = "UNAVAILABLE_FILE"
                                client_socket.sendall(response.encode('utf-8')) 
                            
                        else:
                            print("peer not able to send the file fragment")
                            response = "Peer_was_not able to send the file fragment"
                            client_socket.sendall(response.endswith('utf-8'))
                            #reject seeder serve

                    finally:
                        #client_socket.close()
                        self.Seeder_serve_lock.acquire()
                        self.selectedSeederPort = None
                        self.seeder_waitlist.remove((client_socket, client_address))
                        self.Seeder_serve_lock.release()          
            except ConnectionError:
                continue
            except Exception as e:
                raise e
                #sys.exit()
                break
   

    def select(self, list):
        return choice(list)

    def select_clients_response(self):
        if self.seeder_waitlist:
            selected_client = choice(self.seeder_waitlist) #choose a client from the list at random
            self.selectedSeederPort = selected_client[1] #mark the selected socket
            self.response_seeder_serve_event.set() #wake up the process waiting on this event

        #reset global timer flag
        self.fulfil_seeder_serve = False
        self.response_seeder_serve_event.clear()
        #reset event flag

    def select_client(self):
        if self.collab_waitdict:
            action = choice([1 , 1, 2, 2, 2, 2, 3, 3, 3, 3])
            #gia debugging to apo katw
            #action = 1
            client_port = ""
            #print(action)
            #print("ACTIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOON")
            if action == 1:
                #print("kanw action 1")
                lista_keys = list(self.collab_waitdict.keys())
                client_key = choice(lista_keys)
                selected_client = self.collab_waitdict[client_key]
                client_port = selected_client[1][1]
                #print("telos action 1")
                #prwtosenario
            elif action == 2:
                #print("kanw action 2")
                eval = list()
                peer_ids_waiting = ""
                #print("arxizw checkactives")
                for p in self.collab_waitdict:
                    checkactiveresp = self.CheckActive(p[0], p[1], self.collab_waitdict[p])
                    if checkactiveresp[0].__contains__("POSITIVE_ACK"):
                        eval.append(checkactiveresp[1])
                    else:
                        eval.append(float('inf'))
                    peer_ids_waiting += (p + "|")
                #print("telos checkactives")
                with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
                    s.connect((self.serverHOST, self.serverPORT))
                    message = f"PEER_IDS_FOR_SELECT {peer_ids_waiting}"
                    s.sendall(message.encode('utf-8'))
                    #print(f"steila {message}")
                    resp = s.recv(1024).decode('utf-8')
                    #print(f"dexthka {resp}") 
                    peer_string_list = resp.split('|')
                    peer_string_list= [element for element in peer_string_list if element != '']
                peers_waiting = list()
                #print(peer_string_list)
                for s in peer_string_list:
                    peers_waiting.append(PeerINFO.deserialize(s))

                peers_score_list = self.evaluate_peers(peers_waiting, eval)
                for i in range(1, len(peers_waiting)):
                    key = peers_score_list[i]
                    peer = peers_waiting[i]
                    j = i - 1
                    while j >= 0 and key < peers_score_list[j]:
                        peers_waiting[j + 1] = peers_waiting[j]
                        peers_score_list[j + 1] = peers_score_list[j]
                        j -= 1
                    peers_waiting[j + 1] = peer
                    peers_score_list[j + 1] = key
                selected_client = peers_waiting[0]
                client_id = selected_client.token_id
                client_port = self.collab_waitdict[int(client_id)][1][1]
                #print("telos action 2")
                #deutereosenario
            elif action == 3:
                #print("kanw action 3")
                maxrecv = 0
                for i in self.recvFrom:
                    if self.recvFrom[i] > maxrecv:
                        selected_client = i
                client_id = selected_client.token_id
                if client_id in list(self.collab_waitdict.keys()):
                    client_port = self.collab_waitdict[int(client_id)][1][1]
                #print
                #print("telso action 3")
                #tritosenario     
            self.selectedCollabPeerPort = client_port
            #print(self.selectedCollabPeerPort)
            self.response_collab_event.set() 
        self.fulfil_collab = False
        self.response_collab_event.clear()

    def console_menu(self):
        while True:
            print("\nPeer Menu:")
            print("1. List available files")
            print("2. Download a file")
            print("3. File details")
            print("4. Logout")
            print("5. Exit")

            choice = input("\nEnter your choice (1-5): ")

            if choice == "1":
                self.list()
                
            elif choice == "2":
                filename = input("\nEnter the filename to download: ")
                self.Download(filename)
            elif choice == "3":
                filename = input("\nEnter the filename to get peers that host it: ")
                peer_infos = self.details(filename)[0]
                
                for p in peer_infos:
                    print(p)
                    
                    resp, rtt = self.CheckActive(p.ip, int(p.port), p.token_id)
                    
                    print(resp + " TIME TO RESPOND:"+ str(rtt) +" ms")
                    print(self.evaluate_peers([p] , [rtt]))
                #check each peer if they are active evaluate them 
                
                # Get the list of available files
                #peers_info = [] # Get the details of the peers sharing the available files
                #for filename in available_files:
                #    peers_info.extend(self.details(filename))
                #self.share_directory(peers_info)
            elif choice == "4":
                print("\nLogging out...")
                print("\nRESULT "+ self.logout())
                return True
            elif choice == "5":
                print("\nExiting...")
                return False
            else:
                print("\nInvalid choice. Please try again.")

    def registration_menu(self):
        registered = False
        while not registered:
            print("\nRegistration:")
            username = input("Please enter your name: ")
            password = input("Please enter your password: ")
            registered = self.register(username=str(username), password=str(password)) # Register the user
            self.login(username, password) # Automatically login after registration
    
    def login_menu(self):
        logged_in = False
        while not logged_in:
            print("\nLogin:")
            username = input("Please enter your username: ")
            password = input("Please enter your password: ")
            logged_in = self.login(username=str(username), password=str(password)) # Login the user
        


def main():
    
    #num = sys.argv[1]
    while True:
        print("WELCOME TO THE PEER-TO-PEER FILE SHARING SYSTEM !")
        p1 = Peer(3) # Hardcoded FOR DEBUGGING !


        decided = False
        while not decided:
            inp = str.capitalize(input("Enter 'R' for registration or 'L' for login: "))
            if inp == 'R':
                p1.registration_menu()
                decided = True
            elif inp == 'L':
                p1.login_menu()
                decided = True
            else:
                print("Invalid input. Please try again.")
        
        while True:
            ans = p1.console_menu()
            if ans == True:
                if p1.logout():
                    print("\nLogged out successfully.")
                    break
            else:
                print("GOODBYE")
                return

main()

