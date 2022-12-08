from csv import reader
import ctypes
def int_overflow(val):
    maxint = 2147483647
    if not -maxint-1 <= val <= maxint:
        val = (val + (maxint + 1)) % (2 * (maxint + 1)) - maxint - 1
    return val


def unsigned_right_shitf(n,i):
    # 数字小于0，则转为32位无符号uint
    if n<0:
        n = ctypes.c_uint32(n).value
    # 正常位移位数是为正数，但是为了兼容js之类的，负数就右移变成左移好了
    if i<0:
        return -int_overflow(n << abs(i))
    #print(n)
    return int_overflow(n >> i)
    

def getOrangeCounter(data):
    orangeCnt = 0
    purpleCnt = 0
    blueCnt = 0
    grayCnt = 0
    specialCnt = 0
    weaponCnt = 0

    color = getCampText(data)
    grayCnt = grayCnt + 1;



    color = getOccupationText(data)
    if color == "ORANGE":
        orangeCnt = orangeCnt +1
    elif color == "PURPLE":
        purpleCnt = purpleCnt + 1;
    elif color == "BLUE":
        blueCnt = blueCnt + 1;
    elif color == "GRAY":
        grayCnt = grayCnt+ 1;

    color = getLevelText(data)
    if color == "ORANGE":
        orangeCnt = orangeCnt +1
    elif color == "PURPLE":
        purpleCnt = purpleCnt + 1;
    elif color == "BLUE":
        blueCnt = blueCnt + 1;
    elif color == "GRAY":
        grayCnt = grayCnt+ 1;

    #special info
    color = getSpecialText(data)
    if color == "ORANGE":
        specialCnt = specialCnt + 1
    else :
        grayCnt = grayCnt + 1

    color = getModelText(data)
    if color == "ORANGE":
        orangeCnt = orangeCnt +1
    elif color == "PURPLE":
        purpleCnt = purpleCnt + 1;
    elif color == "BLUE":
        blueCnt = blueCnt + 1;
    elif color == "GRAY":
        grayCnt = grayCnt + 1;


    color = getHealText(data)
    if color == "ORANGE":
        orangeCnt = orangeCnt +1
    elif color == "PURPLE":
        purpleCnt = purpleCnt + 1;
    elif color == "BLUE":
        blueCnt = blueCnt + 1;
    elif color == "GRAY":
        grayCnt = grayCnt + 1;

    color = getPowerText(data)
    if color == "ORANGE":
         orangeCnt = orangeCnt +1
    elif color == "PURPLE":
        purpleCnt = purpleCnt + 1;
    elif color == "BLUE":
        blueCnt = blueCnt + 1;
    elif color == "GRAY":
        grayCnt = grayCnt + 1;   

    color = getArmorText(data)
    if color == "ORANGE":
        orangeCnt = orangeCnt +1
    elif color == "PURPLE":
        purpleCnt = purpleCnt + 1;
    elif color == "BLUE":
        blueCnt = blueCnt + 1;
    elif color == "GRAY":
        grayCnt = grayCnt + 1;

    color  = getSpecialWeaponText(data)
    if color == "ORANGE":
        weaponCnt  = weaponCnt + 1
    else:
        grayCnt = grayCnt + 1
    return orangeCnt,purpleCnt,blueCnt,grayCnt,specialCnt,weaponCnt

def sparkModelExist(data):
    value = getModelValueByItemData(data)
    if value == 5 :
        return 1
    return 0

def getCampText( tokenData):
    value = getCampValueByItemData(tokenData)
    color = getCampColor(value)
    return color
def getOccupationText( tokenData):
    value = getOccupationValueByItemData(tokenData)
    color = getOccupationColor(value)
    return color


def getLevelText(  tokenData):
    value = getLevelValueByItemData(tokenData)
    color = getLevelColor(value)
    return color

def getSpecialText(  tokenData):
    value = getSpecialValueByItemData(tokenData)
    if (value == 0):
        color = "GRAY"
    else:
        color = "ORANGE"
    return color

def getModelText(  tokenData):
    value = getModelValueByItemData(tokenData)
    color = getModelColor(value)
    return color

def getHealText(  tokenData):
    value = getHealthValueByItemData(tokenData)
    color = getHealthColor(value)
    return color


def getPowerText(  tokenData):
    value = getPowerValueByItemData(tokenData)
    color = getPowerColor(value)
    return color

def getArmorText(  tokenData):
    value = getArmorValueByItemData(tokenData)
    color = getArmorColor(value)
    return color

def getSpecialWeaponText(  tokenData):
    value = getSpecialWeaponsValueByItemData(tokenData)
    if value == 0:
        color = "GRAY"
    else:
        color = "ORANGE"
    return color

def getCampColor(  value):
    if (value == 1):
        return "GRAY"
    elif (value == 2):
        return "GRAY"
    elif (value == 3):
        return "GRAY"
    elif (value == 4):
        return "GRAY"

def getCampValueByItemData( itemData):
    return unsigned_right_shitf(itemData,8) & 0xFF


def getOccupationColor(  value):
    if (value == 1):
        return "ORANGE"
    elif (value == 2):
        return "ORANGE"
    elif (value == 3):
        return "ORANGE"
    elif (value == 4):
        return "PURPLE"
    elif (value == 5):
        return "PURPLE"
    elif (value == 6):
        return "BLUE"
    elif (value == 7):
        return "BLUE"
    elif (value == 8):
        return "GRAY"
    elif (value == 9):
        return "GRAY"


def getOccupationValueByItemData( itemData):
    return unsigned_right_shitf(itemData ,16) & 0xFF

def getLevelColor(  value):
    if (value >= 90 and value < 100):
        return "ORANGE"
    elif (value >= 60 and value < 89) :
        return "PURPLE"
    elif (value >= 40 and value < 59) :
        return "BLUE"
    elif (value >= 0 and value < 39) :
        return "GRAY"

def getLevelValueByItemData( itemData):
    return unsigned_right_shitf(itemData ,24) & 0xFF


def getSpecialColor(  value) :
    return "ORANGE"


def getSpecialValueByItemData( itemData):
    return unsigned_right_shitf(itemData,32) & 0xFF


def getModelColor(  value) :
    if (value == 1) :
        return "ORANGE"
    elif (value == 2) :
        return "ORANGE"
    elif (value == 3) :
        return "ORANGE"
    elif (value == 4) :
        return "ORANGE"
    elif (value == 5) :
        return "ORANGE"
    elif (value == 6) :
        return "PURPLE"
    elif (value == 7) :
        return "PURPLE"
    elif (value == 8) :
        return "PURPLE"
    elif (value == 9) :
        return "PURPLE"
    elif (value == 10) :
        return "PURPLE"
    elif (value == 11) :
        return "PURPLE"
    elif (value == 12) :
        return "PURPLE"
    elif (value == 13) :
        return "BLUE"
    elif (value == 14) :
        return "BLUE"
    elif (value == 15) :
        return "BLUE"
    elif (value == 16) :
        return "BLUE"
    elif (value == 17) :
        return "BLUE"
    elif (value == 18) :
        return "BLUE"
    elif (value == 19) :
        return "BLUE"
    elif (value == 20) :
        return "GRAY"
    elif (value == 21) :
        return "GRAY"
    elif (value == 22) :
        return "GRAY"
    elif (value == 23) :
        return "GRAY"
    elif (value == 24) :
        return "GRAY"
    elif (value == 25) :
        return "GRAY"


def getModelValueByItemData( itemData):
    return unsigned_right_shitf(itemData, 40) & 0xFF

def getHealthColor(  value) :
    if (value >= 90 and value < 100) :
        return "ORANGE"
    elif (value >= 60 and value < 89) :
        return "PURPLE"
    elif (value >= 40 and value < 59) :
        return "BLUE"
    elif (value >= 0 and value < 39) :
        return "GRAY"

def getHealthValueByItemData( itemData):
    return unsigned_right_shitf(itemData ,48) & 0xFF


def getPowerColor(  value) :
    if (value >= 90 and value < 100) :
        return "ORANGE"
    elif (value >= 60 and value < 89) :
        return "PURPLE"
    elif (value >= 40 and value < 59) :
        return "BLUE"
    elif (value >= 0 and value < 39) :
        return "GRAY"

def getPowerValueByItemData( itemData):
    return unsigned_right_shitf(itemData , 56) & 0xFF

def getArmorColor(  value) :
    if (value >= 90 and value < 100) :
        return "ORANGE"
    elif (value >= 60 and value < 89) :
        return "PURPLE"
    elif (value >= 40 and value < 59) :
        return "BLUE"
    elif (value >= 0 and value < 39) :
        return "GRAY"

def getArmorValueByItemData( itemData):
    return unsigned_right_shitf(itemData , 64) & 0xFF


def getSpecialWeaponsColor(  value):
    return "ORANGE"

def getSpecialWeaponsValueByItemData( itemData):
    return unsigned_right_shitf(itemData , 72) & 0xFF


counterfile = 'orange.csv'
sparkScore = 50
specialScore = 20
weaponScore = 15
orangeScore = 10
purpleScore = 5
blueScore = 3
grayScore = 1
orgCnt = 0
purCnt = 0
blueCnt = 0
grayCnt = 0
specialCnt = 0
weaponCnt = 0

with open(counterfile, 'a+') as f:
# open file in read mode
    with open('data.txt', 'r') as read_obj:
        # pass the file object to reader() to get the reader object
        csv_reader = reader(read_obj)
        # Iterate over each row in the csv using reader object
        f.write("tokenId ,itemData,orgCnt,purCnt, blueCnt, grayCnt  ,specialCnt,weaponCnt,spark,score \n")
        for row in csv_reader:
            # row variable is a list that represents a row in csv
            itemData = int(row[1])
            orgCnt,purCnt,blueCnt,grayCnt,specialCnt,weaponCnt = getOrangeCounter(itemData)
            exist = sparkModelExist(itemData)
            score = (orgCnt*orangeScore + purCnt* purpleScore + blueCnt*blueScore + grayCnt*grayScore + specialCnt*specialCnt + weaponCnt*weaponScore)
            score = (score + exist*sparkScore)
            tokenId = str(row[0])
            #s = "{Id} ,{Data},{org} ,{pur},{blue},{gray},{special},{weapon},{sc} \n"
            # Id,Data,orangeCnt,Spark
            #s.format(Id = tokenId, Data = str(itemData),org = str(orgCnt),pur = str(purCnt),blue = str(blueCnt),gray = str(grayCnt),special = str(specialCnt),weapon = str(weaponCnt),sc = str(score))
            f.write(tokenId+ ","+ str(itemData) + "," + str(orgCnt) +","+ str(purCnt) +","+ str(blueCnt) +","+ str(grayCnt) +","+ str(specialCnt) +","+str(weaponCnt)+","+str(exist)+","+str(score)+" \n")