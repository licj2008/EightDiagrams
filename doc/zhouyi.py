import operator

import requests
from bs4 import BeautifulSoup
import urllib.request

new_data = [] #存放64卦url
guaList =[]

import re
def getrevalue(str,pa):
    return re.findall(pa, str)

#每一卦的数据结构
class Gua(object):
    def __init__(self, name,yuanwen,jiuyao1,jiuyao2,jiuyao3,jiuyao4,jiuyao5,jiuyao6,hugua,cuogua,zonggua):
        self.name = name
        self.yuanwen = yuanwen
        self.jiuyao1 = jiuyao1
        self.jiuyao2 = jiuyao2
        self.jiuyao3 = jiuyao3
        self.jiuyao4 = jiuyao4
        self.jiuyao5 = jiuyao5
        self.jiuyao6 = jiuyao6
        self.hugua = hugua
        self.cuogua = cuogua
        self.zonggua = zonggua


def getPageData(aurl):
    response = requests.get(aurl)
    html_doc = response.content.decode('utf-8')
    bs = BeautifulSoup(html_doc)
    gua_wp = bs.find('div', class_='gua_wp')
    #print(gua_wp)
    name= gua_wp.find('div',class_='gua_toptt f14 fb cf').text
    guanlianlist = gua_wp.find('div',class_='img64_a').findAll('tr')
    indexguanlian = 0
    for guanlian in guanlianlist: #关系的卦，互卦如错卦综卦
        indexguanlian= indexguanlian+1
        # if indexguanlian == 1: #下载
        #     tdlist = guanlian.findAll('td')
        #     indextd = 0
        #     for tditem in tdlist:
        #         indextd = indextd + 1
        #         if indextd == 1:
        #             imgurl = tditem.find('img')['src']
        #             print("image="+imgurl)
        #             urllib.request.urlretrieve(imgurl,'gua'+getrevalue(name, '第(.*?)卦').__getitem__(0)+'.png')
        if indexguanlian == 3:
            tdlist = guanlian.findAll('td')
            indextd = 0
            for tditem in tdlist:
                indextd = indextd+1
                if indextd ==2:
                    hugua =tditem.text
                if indextd ==3:
                    cuogua =tditem.text
                if indextd ==4:
                    zonggua =tditem.text
            # print(guanlian.findAll('td'))
    print(name)
    bodys =[]
    bodys = gua_wp.findAll('div',{'class':'gualist tleft f14 lh25'})
    ii =0
    for aitem in bodys:
        if ii == 0:
           yuanwen = aitem
        if ii == 1:
           jiuyao1 = aitem
        if ii == 2:
           jiuyao2 = aitem
        if ii == 3:
            jiuyao3 = aitem
        if ii == 4:
            jiuyao4 = aitem
        if ii == 5:
            jiuyao5 = aitem
        if ii == 6:
            jiuyao6 = aitem
        if ii >0 : #下载每一卦的六爻图
            imgurl = aitem.find('img')['src']
            if operator.contains(imgurl,'www.zhouyi.cc/uploads/allimg'):
                urllib.request.urlretrieve(imgurl,'gua'+getrevalue(name, '第(.*?)卦').__getitem__(0)+'_'+str(ii)+'.png')
        ii = ii + 1
    # for aitem in bodys:
    #     if ii == 0:
    #        yuanwen = aitem.text
    #     if ii == 1:
    #        jiuyao1 = aitem.text
    #     if ii == 2:
    #        jiuyao2 = aitem.text
    #     if ii == 3:
    #         jiuyao3 = aitem.text
    #     if ii == 4:
    #         jiuyao4 = aitem.text
    #     if ii == 5:
    #         jiuyao5 = aitem.text
    #     if ii == 6:
    #         jiuyao6 = aitem.text
    #     ii = ii + 1

    curGua = Gua(name,yuanwen,jiuyao1,jiuyao2,jiuyao3,jiuyao4,jiuyao5,jiuyao6,hugua,cuogua,zonggua)
    guaList.append(curGua)


url = 'https://www.zhouyi.cc/zhouyi/yijing64/'
response = requests.get(url)

html_doc = response.content.decode('utf-8')
bs = BeautifulSoup(html_doc)
zhlist = bs.find('div',class_='zhlist')
li_list = zhlist.find_all('li')

index = 0
for item in zhlist.find_all("a"):
    cur_url = "https://www.zhouyi.cc" + item.get("href")
    #https://www.zhouyi.cc/zhouyi/yijing64/4103.html
    index = index + 1
    if (index % 2 == 1):#todo index % 2 == 1
        new_data.append(cur_url)
        getPageData(cur_url)
        # print(index,cur_url)



import pandas as pd
numlist=[]
namelist=[]
name2list=[]
updownlist=[]
hugua =[]
huguaname =[]
cuogua=[]
cuoguaname=[]
zonggua=[]
zongguaname=[]
yuanwenlist=[]
yuanwen1list=[]
yuanwen2list=[]
jiuyao1list=[]
jiuyao2list=[]
jiuyao3list=[]
jiuyao4list=[]
jiuyao5list=[]
jiuyao6list=[]
for item in guaList:
    num = getrevalue(item.name, '第(.*?)卦').__getitem__(0)
    numlist.append(num)
    namelist.append(getrevalue(item.name, '_(.*?)\(').__getitem__(0))
    name2list.append(getrevalue(item.name, '\((.*?)\)').__getitem__(0))
    updownlist.append(getrevalue(item.name, '(.?上.?下)').__getitem__(0))
    yuanwen = item.yuanwen.replace("\r\n","")
    yuanwenlist.append(yuanwen)
    print("do ="+num)
    if (num =="11") | (num =="2"):
        yuanwen1list.append("")
        yuanwen2list.append("")
    else:
        yuanwen1list.append(getrevalue(yuanwen, '大象：(.*?)运势').__getitem__(0))
        yuanwen2list.append(getrevalue(yuanwen, '运势：(.*?)事业').__getitem__(0))
    jiuyao1list.append(item.jiuyao1)
    jiuyao2list.append(item.jiuyao2)
    jiuyao3list.append(item.jiuyao3)
    jiuyao4list.append(item.jiuyao4)
    jiuyao5list.append(item.jiuyao5)
    jiuyao6list.append(item.jiuyao6)
    hugua.append(getrevalue(item.hugua, '第(.*?)卦').__getitem__(0))
    huguaname.append(getrevalue(item.hugua, '：(.*?)\(').__getitem__(0))
    cuogua.append(getrevalue(item.cuogua, '第(.*?)卦').__getitem__(0))
    cuoguaname.append(getrevalue(item.cuogua, '：(.*?)\(').__getitem__(0))
    zonggua.append(getrevalue(item.zonggua, '第(.*?)卦').__getitem__(0))
    zongguaname.append(getrevalue(item.zonggua, '：(.*?)\(').__getitem__(0))

data = {
    'num':numlist,
    'name': namelist,
    'name2': name2list,
        'num': numlist,
        'updown': updownlist,
        'yuanwen': yuanwenlist,
        'daxiang': yuanwen1list,
        'yunshi': yuanwen2list,
        'hugua': hugua,
        'huguaname': huguaname,
        'cuogua': cuogua,
        'cuoguaname': cuoguaname,
        'zonggua': zonggua,
        'zongguaname': zongguaname
        # 'jiuyao1':jiuyao1list,
        # 'jiuyao2':jiuyao2list,
        # 'jiuyao3':jiuyao3list,
        # 'jiuyao4':jiuyao4list,
        # 'jiuyao5':jiuyao5list,
        # 'jiuyao6':jiuyao6list
        }

df = pd.DataFrame(data)

# 将数据存储到CSV文件中
df.to_csv('yidata334.csv', index=False)



