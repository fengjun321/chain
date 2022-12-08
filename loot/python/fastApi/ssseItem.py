import json
import ctypes
import base64

image_prefix = 'https://intro.firework.games/ssse/'
nft_name = [
    'Gemini-Light','Neptune','Gemini-Warrior','Colossus','Gemini-Elite','Megalith','Enlightenment',
    'Daylight','Aurora','Photon','Enlightenment_Limited','Type-Mercenary','Type-Silverstar','Type-Orion',
    'Trailblazer','Star','Galaxy','Eternal'
    ]
nft_url_template = '<div class="sketchfab-embed-wrapper"> <iframe title="{name}" frameborder="0" allowfullscreen mozallowfullscreen="true" webkitallowfullscreen="true" allow="autoplay; fullscreen; xr-spatial-tracking" xr-spatial-tracking execution-while-out-of-viewport execution-while-not-rendered web-share src="{embed_url}autospin=1&camera=0&ui_infos=0&ui_stop=0&ui_inspector=0&ui_watermark_link=0&ui_watermark=0&ui_ar=0&ui_help=0&ui_settings=0&ui_fullscreen=0&ui_annotations=0"> </iframe> </div>'
nft_url = [
    'https://sketchfab.com/models/8aecb191289a430f8d2536aab628df46/embed?',
    'https://sketchfab.com/models/366e658335de45e79be0f65340dccfec/embed?',
    'https://sketchfab.com/models/62eaeb61f7b44409809949bc66fe657c/embed?',
    'https://sketchfab.com/models/c5b2bfa942d549ab8bb111932296b6b6/embed?',
    'https://sketchfab.com/models/b18d6ca74d4249d381e045fe85099057/embed?',
    'https://sketchfab.com/models/fafd247fb6d040fa93f1ba88bbf36e24/embed?',
    'https://sketchfab.com/models/7aefeb4cbfc048f99c38d1377f873fb7/embed?',
    'https://sketchfab.com/models/cf9deac0b6a54c3689d108cf4e1bb822/embed?',
    'https://sketchfab.com/models/4efa7ca03683489a82eb07abec2fe9de/embed?',
    'https://sketchfab.com/models/131103a8179e4c2b9a3ec2af3b823d1d/embed?',
    'https://sketchfab.com/models/dee87e7b44cc42a2acf45edf8077d2ca/embed?',
    'https://sketchfab.com/models/ccb4308c0d2a487a8193ae856fa68b5d/embed?',
    'https://sketchfab.com/models/8568aaff03144962bd98993b72f213a4/embed?',
    'https://sketchfab.com/models/4932556b9d9d4a8fa1ab592dc1184004/embed?',
    'https://sketchfab.com/models/16e8854730cc45768a316cc564156b05/embed?',
    'https://sketchfab.com/models/46a79c382954430aae2cf1aeedd9e92a/embed?',
    'https://sketchfab.com/models/caec22f09d74494391ab901627dbb49c/embed?',
    'https://sketchfab.com/models/9e2c1b0333004f13bbf99f50ac7defce/embed?'
]
nft_rank = ['Basic',
            'Advanced',
            'Souvenir'
]
nft_desc = [
    'A general-purpose Spaceship, known for its high-speed velocity, and its warhead-shaped cylindrical cabin design is sufficient to pierce the starry sky screen. Strong mobility and relatively less attack power. The defense is relatively less.',
    'A general-purpose stealth spaceship with the charming blue color like the Neptune. With advanced anti-electromagnetic interference and radar detection functions, it becomes an invisible killer in the universe, with high mobility and excellent armor.',
    'A Standard-class Spaceship with strong power. Gemini-Elite is an excellent spaceship suitable for various combat environments. It adopts a retro fighter appearance and is equipped with an intelligent combat system, upgraded to crimson camouflage. It is a rare weapon of war. It is equipped with 9 combat modules as standard.',
    'A heavy combat spaceship with lesser maneuverability but a very hard armor layer, equipped with ion cracking cannons to provide powerful fire support. It has seven combat modules and can make fast long-distance flight. “If the colossus can be by my side, I will feel very relieved.”',
    'A Standard-class Spaceship with strong power. Gemini-Elite is an excellent spaceship suitable for various combat environments. It adopts a retro fighter appearance and is equipped with an intelligent combat system, upgraded to crimson camouflage. It is a rare weapon of war. It is equipped with 9 combat modules as standard.',
    'A heavy combat spaceship with lesser maneuverability, like a war boulder. It has a very hard armor layer, and is equipped with ion cracking guns to provide powerful fire support. It possess up to 10 combat modules.',
    'Enlightenment is a lightweight high-speed combat starship with dark classic camouflage, high concealment and strong mobility. Equipped with 12 lightweight modules.',
    'A lightweight high-speed combat spaceship with classic red and white camouflage. It is a ray of morning light that lights up the deep universe. Equipped with 12 lightweight modules.',
    'A light weighted high-speed combat spaceship, with gorgeous camouflage light belts like colored glaze in high-speed movement and thus have a certain hallucinogenic effect. It features excellent mobile combat capability, and is equipped with 14 lightweight modules.',
    'A giant mothership with many multi-functional modules, a heavy war machine suitable for long-distance interstellar conquest, equipped with nearly a hundred small combat spaceships ready for combat at any time, it is a well-deserved space carrier. "The children of the beast are injected into the galaxy like photons."',
    'Enlightenment_limited is a lightweight high-speed combat starship with limited camouflage of starry-sky color, high concealment and strong mobility. Equipped with 14 lightweight modules.',
    'Type-Mercenary, covered with battle marks on the exterior will not affect its excellent combat performance. This is a cutting-edge training machine. After retiring, it provides important help for the training of the interstellar warriors. Its mobility and combat attributes are at an intermediate level.',
    'Type-Silverstar is the latest fast fighting spaceship designed under the framework of the Type-Mercenary basic hull. Its mobility and combat attributes are at an intermediate level. And three new technical modules have been added to additionally improve the overall combat attributes.',
    'Type-Orion: the latest fast fighting spaceship designed under the framework of the Type-Silverstar basic hull. Its mobility and combat attributes are at a high level. With a gorgeous appearance and five new technical modules, the overall combat attributes are additionally improved.',
    'The Terminator: The extreme color blending of red and black makes this spaceship full of offensiveness. It is a medium-sized lightweight ship with great attack power and maneuverability. It has strong adaptability to multiple environments and can meet all combat needs. “This is a commemoration of those pioneering adventurers in the Spark Era universe”.',
    'A lightweight combat spaceship with a stylish and colorful exterior design and extremely high mobile combat capabilities. It supports long-distance wrap feature. It has multiple replaceable modules for support, and up to 8 combat module positions. “This is a commemoration of those pioneering adventurers in the Spark Era universe”.',
    'A heavy armored combat spaceship with a stylish and colorful appearance design, extremely high combat capability and mobile combat capability. It supports long-distance curvature leap forward. It has multiple replaceable modules for support, and up to 12 combat module positions. “This is a commemoration of those pioneering adventurers in the Spark Era universe”.',
    'A heavy combat transport ship, with an extremely modest and simple appearance design, extremely high combat capability and strong strategic material supply capability. It is a transit station for interstellar combat and has multiple replaceable modules for support, and up to 18 combat module positions. “This is a commemoration of those pioneering adventurers in the Spark Era universe”.'
]

def unsigned_right_shitf(n,i):
    # 数字小于0，则转为32位无符号uint
    if n<0:
        n = ctypes.c_uint32(n).value
    # 正常位移位数是为正数，但是为了兼容js之类的，负数就右移变成左移好了
    if i<0:
        return - (n << abs(i))
    #print(n)
    return (n >> i)

class SpaceShipData():
    def __init__(self) :
        self.type,self.nameId,self.level,self.duration,self.version,self.basicSlotLimit, \
        self.basicSlotCurrent,self.leaderSlotLimit,self.leaderSlotCurrent,self.SciSlotLimit,self.SciSlotCurrent, \
        self.CommanderSlotLimit,self.CommanderSlotCurre,self.SsoldierSlotLimit,self.SsoldierCurrent, \
        self.CsoldierSlotLimit, self.CsoldierSlotCurre = 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    def parseData(self,data):
        self.type = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.nameId = data & 0xFFFF
        data = unsigned_right_shitf(data,16)

        self.level = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.duration = data & 0xFFFF
        data = unsigned_right_shitf(data,16)

        self.version = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.basicSlotLimit = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.basicSlotCurrent = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.leaderSlotLimit = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.leaderSlotCurrent = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.SciSlotLimit = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.SciSlotCurrent = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.CommanderSlotLimit = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.CommanderSlotCurre = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.SsoldierSlotLimit = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.SsoldierCurrent = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.CsoldierSlotLimit = data & 0xFF
        data = unsigned_right_shitf(data,8)

        self.CsoldierSlotCurre = data & 0xFF
        data = unsigned_right_shitf(data,8)



    def getSsseName(self):
        return nft_name[self.nameId - 1]
    def getImageUrl(self):
        return image_prefix +self.getShipRank()+'/'+self.getSsseName()+'/'+self.getSsseName()+'_Market.png'
   
    def getSsseDesc(self):
        return nft_desc[self.nameId - 1]
    def getSsseModelUrl(self):
        return base64.b64encode(str.format(nft_url_template,name = self.getSsseName(),embed_url = nft_url[self.nameId-1]).encode('ascii'))
    def getShipRank(self):
        return nft_rank[self.type]
    def getShipLevel(self):
        return self.level
    def getShipPower(self):
        power = 0
        if self.type != 0:
            if self.version == 0:
                power = self.SciSlotLimit * 35 + self.CommanderSlotLimit *20 +self.SsoldierSlotLimit * 15 +self.CsoldierSlotLimit *5;
            elif self.version ==1:
                power = self.SciSlotLimit * 45 + self.CommanderSlotLimit *30 +self.SsoldierSlotLimit * 25 +self.CsoldierSlotLimit *15;
        return power
    def getbasicSlotLimit(self):
        return self.basicSlotLimit
    def getbasicSlotCurrent(self):
        return self.basicSlotCurrent
    def getleaderSlotLimit(self):
        return self.leaderSlotLimit
    def getleaderSlotCurrent(self):
        return self.leaderSlotCurrent
    def getCommanderLimit(self):
        return self.CommanderSlotLimit
    def getCommanderCurrent(self):
        return self.CommanderSlotCurre;
    def getSciSlotLimit(self):
        return self.SciSlotLimit
    def getSciSlotCurrent(self):
        return self.SciSlotCurrent
    def getSsoldierSlotLimit(self):
        return self.SsoldierSlotLimit
    def getSsoldierSlotCurrent(self):
        return self.SsoldierSlotLimit
    def getCsoldierSlotLimit(self):
            return self.CsoldierSlotLimit
    def getCsoldierSlotCurrent(self):
        return self.CsoldierSlotCurre

