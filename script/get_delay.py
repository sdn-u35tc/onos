import requests, json
from requests.auth import HTTPBasicAuth

ip = "192.168.89.130"
auth = HTTPBasicAuth("karaf", "karaf")


# 获取时延信息，返回json
def get_delay(controller_ip):
    delay_url = "http://{}:8181/onos/get-link-delay/GetDelay".format(controller_ip)
    headers = {
        "Accept": "application/json"
    }

    resp = requests.get(url=delay_url, headers=headers, auth=auth)

    return resp.status_code, resp.text


# 获取端口关系的拓扑
def get_graph(controller_ip):
    graph_url = "http://{}:8181/onos/choice-best-path/graph".format(controller_ip)
    headers = {
        "Accept": "application/json"
    }

    resp = requests.get(url=graph_url, headers=headers, auth=auth)

    return resp.status_code, resp.text


# 获取主机和交换机关系的拓扑
def get_host(controller_ip):
    host_url = "http://{}:8181/onos/device-and-host/devicehost".format(controller_ip)
    headers = {
        "Accept": "application/json"
    }

    resp = requests.get(url=host_url, headers=headers, auth=auth)

    return resp.status_code, resp.text


# 生成选路所用的拓扑
def for_path():
    status1, de_resp = get_delay(ip)
    status2, gr_resp = get_graph(ip)
    if status1 == 200 and status2 == 200:
        delay_info = json.loads(de_resp)
        graph_info = json.loads(gr_resp)

        for key in graph_info:
            neig = graph_info[key]
            for i in range(len(neig)):
                if key[:-2] != neig[i][:-2]:
                    neig[i] += " "+str((delay_info[key[:-2]+' '+neig[i][: -2]]+delay_info[neig[i][: -2]+' '+key[:-2]])/2)

                else:
                    neig[i] += " "+str(100000)

    return graph_info


# 生成前端所需的拓扑格式 
def for_show():
    status1, de_resp = get_delay(ip)

    status3, ho_resp = get_host(ip)
    graph_show = {
        "nodes": [],
        "links": []
    }
    if status1 == 200 and status3 == 200:
        delay_info = json.loads(de_resp)
        host_info = json.loads(ho_resp)

        for key in host_info:
            node = {"id": key}
            graph_show["nodes"].append(node)
            host = host_info[key]
            for i in range(len(host)):
                node = {"id": host[i]}
                graph_show["nodes"].append(node)
                link = {
                    "source": key,
                    "target": host[i],
                    "type": "100000"
                }
                graph_show["links"].append(link)

        for key in delay_info:
            link = {
                "source": key[:19],
                "target": key[-19:],
                "type": str((delay_info[key]+delay_info[key[-19:]+" "+key[:19]])/2)
            }
            graph_show["links"].append(link)

    return graph_show


# 测试
if __name__ == "__main__":
    graph_path=for_path()
    graph_show=for_show()
    print(json.dumps(graph_path,indent=2))
    print(json.dumps(graph_show, indent=2))
