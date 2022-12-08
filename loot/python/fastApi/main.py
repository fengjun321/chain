from operator import imod
from pseItem import getImageUrl,getTraitValue,getClaimed
from egseItem import getEgseImageUrl,getEgseTraitValue
from hseItem import getHseImageUrl,getHseTraitValue
from mbseItem import getMbseImageUrl,getMbseTraitValue
from eggItem import *
from fastapi import FastAPI
from fastapi.encoders import jsonable_encoder
from fastapi.responses import JSONResponse
from fhcItem import HFCData
from csv import reader
from fastapi.middleware.cors import CORSMiddleware
from galaxy import *
from ssseItem import SpaceShipData
from gameItem import *
from aseItem import AseData

app = FastAPI()
origins = [
    "https://www.firework.games/",
    "https://dev.d218840izb7lhf.amplifyapp.com/",
    "http://localhost:3000",
]
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
async def root():
    return {"message": "Hello World"}
@app.get("/score/{id}")
async def getIdScore(id):
    score = 0
    with open('score.csv', 'r') as read_obj:
        # pass the file object to reader() to get the reader object
        csv_reader = reader(read_obj)
        # Iterate overi each row in the csv using reader object
        for row in csv_reader:
            # row variable is a list that represents a row in csv
            if(id == row[0]):
                score = row[9]
                break
    return "{'tokenid':"+str(id)+",'score':"+score+"}"
@app.get("/pse/metadata/{id}/{tokenData}")
async def getPseURI(id,tokenData):
    uriDic = {
        "tokenId":id,
        "name":"PSE(Passport of Spark Era) #"+str(id),
        "image":getImageUrl(int(tokenData)-1),
        "detailimage":getImageUrl(int(tokenData)-1),
        "description":"Passport of Spark Era,will be used to get benefit when the Spark Era game launched.",
        "attributes":[{            
            "trait_type":"type",
            "value":getTraitValue(int(tokenData)-1)
            },
            {
            "trait_type":"claimed",
            "value":False
            }
            ]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))

@app.get("/pse/metadata/{id}/{tokenData}/{claimed}")
async def getPseURI(id,tokenData,claimed):
    uriDic = {
        "tokenId":id,
        "name":"PSE(Passport of Spark Era) #"+str(id),
        "image":getImageUrl(int(tokenData)-1),
        "detailimage":getImageUrl(int(tokenData)-1),
        "description":"Passport of Spark Era,will be used to get benefit when the Spark Era game launched.",
        "attributes":[{            
            "trait_type":"type",
            "value":getTraitValue(int(tokenData)-1)
            },
            {
            "trait_type":"claimed",
            "value":getClaimed(int(claimed))
            }
            ]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))

@app.get("/egse/metadata/{id}/{tokenData}")
async def getEgseURI(id,tokenData):
    uriDic = {
        "tokenId":id,
        "name":"EGSE(Epic Giveaway of Spark Era) #" +str(id),
        "image":getEgseImageUrl(int(tokenData)-1),
        "detailimage":getEgseImageUrl(int(tokenData)-1),
        "description":"Congrats to the gamers who claimed the super Loot NFT spaceships! You will have the chance of receiving our loot box NFT airdrop when the game Spark Era launch.",
        "attributes":[{            
            "trait_type":"type",
            "value":getEgseTraitValue(int(tokenData)-1)
            }]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))
#print(getPseURI(1,2))
@app.get("/hse/metadata/{id}/{tokenData}")
async def getHseURI(id,tokenData):
    uriDic = {
        "tokenId":id,
        "name":"HSE(Halloween of Spark Era) #" + str(id),
        "image":getHseImageUrl(int(tokenData)-1),
        "detailimage":getHseImageUrl(int(tokenData)-1),
        "description":"Happy Halloween.",
        "attributes":[{            
            "trait_type":"type",
            "value":getHseTraitValue(int(tokenData)-1)
            }]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))
    
@app.get("/fhc/metadata/{id}/{tokenData}")
async def getFhcURI(id,tokenData):
    hfc = HFCData()
    hfc.parseData(int(tokenData))
    uriDic = {
        "tokenId":id,
        "name":"FHC(Firework Hero Card) #"+ str(id),
        "image":hfc.getFhcImageUrl(),
        "detailimage":hfc.getFhcDetailImageUrl(),
        "description":"Firework is game-dev platform.",
        "attributes":[
            {            
            "trait_type":"edition",
            "value":hfc.getFhcEditionValue(),
            },
            {            
            "trait_type":"camp",
            "value":hfc.getFhcCampValue(),
            },
            {            
            "trait_type":"rank",
            "value":hfc.getFhcRankValue(),
            },
            {            
            "trait_type":"material",
            "value":hfc.getFhcMaterialValue(),
            },
            {            
            "trait_type":"name",
            "value":hfc.getFhcNameValue(),
            },
            {            
            "trait_type":"Identify",
            "value":hfc.getIdentity(),
            },
            {            
            "trait_type":"Occupation",
            "value":hfc.getProfession(),
            },
            {            
            "trait_type":"Endurance",
            "value":hfc.getEndurance(),
            },
            {            
            "trait_type":"Strength",
            "value":hfc.getStrength(),
            },
            {            
            "trait_type":"Agility",
            "value":hfc.getAgility(),
            },
            {            
            "trait_type":"Armor",
            "value":hfc.getArmor(),
            },
            {            
            "trait_type":"Crit",
            "value":hfc.getCrit(),
            },
            {            
            "trait_type":"Pisonic",
            "value":hfc.getPisonic(),
            },
            {            
            "trait_type":"Spirit",
            "value":hfc.getSpirit(),
            },
            {            
            "trait_type":"Power",
            "value":hfc.getPower(),
            },
            ]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))

@app.get("/mbse/metadata/{id}/{tokenData}")
async def getMbseURI(id,tokenData):
    uriDic = {
        "tokenId":id,
        "name":"MBSE(Mysery Box of Spark Era) #"+ str(id),
        "image":getMbseImageUrl(int(tokenData)-1),
        "detailimage":getMbseImageUrl(int(tokenData)-1),
        "description":"",
        "attributes":[{            
            "trait_type":"type",
            "value":getMbseTraitValue(int(tokenData)-1)
            }]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))
@app.get("/ssse/metadata/{id}/{tokenData}")
async def getSsseURI(id,tokenData):
    ss = SpaceShipData()
    ss.parseData(int(tokenData))
    uriDic = {
        "tokenId":id,
        "name":"SSSE(Space Ship of Spark Era) #"+  str(id),
        "image":ss.getImageUrl(),
        "model":ss.getSsseModelUrl(),
        "description":ss.getSsseDesc(),
        "attributes":[{           
            "trait_type":"Shipname",
            "value":ss.getSsseName(),
            },
            {
            "trait_type":"Rank",
            "value":ss.getShipRank()
            },
            {
            "trait_type":"Level",
            "value":ss.getShipLevel()
            },
            {
                "trait_type":"Power",
                "value":ss.getShipPower()
            },
            {
                "trait_type":"BasicLimit",
                "value":ss.getbasicSlotLimit()
            },
            {
                "trait_type":"BasicCurrent",
                "value":ss.getbasicSlotCurrent()
            },
            {
                "trait_type":"LeaderLimit",
                "value":ss.getleaderSlotLimit()
            }
            ,
            {
                "trait_type":"LeaderCurrent",
                "value":ss.getleaderSlotCurrent()
            },
            {
                "trait_type":"ScientistLimit",
                "value":ss.getSciSlotLimit()
            },
            {
                "trait_type":"ScientistCurrent",
                "value":ss.getSciSlotCurrent()
            },
            {
                "trait_type":"CommanderLimit",
                "value":ss.getCommanderLimit()           
            },
            {
                "trait_type":"CommanderCurrent",
                "value":ss.getCommanderCurrent()
            },
            {
                "trait_type":"StarSoldierLimit",
                "value":ss.getSsoldierSlotLimit()
            }
            ,
            {
                "trait_type":"StarSoldierCurrent",
                "value":ss.getSsoldierSlotCurrent()
            },
            {
                "trait_type":"CommonSoldierLimit",
                "value":ss.getCsoldierSlotLimit()
            }
            ,
            {
                "trait_type":"CommonSoldierCurrent",
                "value":ss.getCsoldierSlotCurrent()
            }
            
            ]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))

@app.get("/galaxy/all")
async def getAllGalaxyInfo():
    galaxy_list = getAllGalaxy()
    return JSONResponse(content=jsonable_encoder(galaxy_list))
@app.get("/galaxy/{id}")
async def getGalaxyInfobyId(id):
    galaxy = getGalaxy(int(id))
    return JSONResponse(content=jsonable_encoder(galaxy))

@app.get("/galaxy/planet/{planet_id}")
async def getPlanetInfobyId(planet_id):
    uriDic = getPlanet(int(planet_id))
    return JSONResponse(content=jsonable_encoder(uriDic))

@app.get("/fpe/metadata/{id}/{tokenData}")
async def getFpeURI(id,tokenData):
    uriDic = {
        "tokenId":id,
        "name":"FPE(Pet Egg of Spark Era) #"+str(id),
        "image":getEggImageUrl(int(tokenData)-1),
        "detailimage":getEggImageUrl(int(tokenData)-1),
        "description":"Pet Egg of Spark Era.",
        "attributes":[{            
            "trait_type":"type",
            "value":getEggTraitValue(int(tokenData)-1)
            }
            ]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))
@app.get("/gameitem/{id}")
async def getGameItemURI(id):
    uriDic = {
        "Id":id,
        "name":"Game Items of Spark Era",
        "image":getGameItemsImageUrl(int(id)-1),
        "detailimage":getGameItemsDetailUrl(int(id)-1),
        "description":getItemDesc(int(id)-1),
        "attributes":[{            
            "trait_type":"rank",
            "value":getItemRank(int(id)-1)
            }
            ]
    }
    return JSONResponse(content=jsonable_encoder(uriDic))
@app.get("/ase/metadata/{id}/{tokenData}")
async def getAseURI(id,tokenData):
    ase = AseData()
    ase.parseData(int(tokenData))
    uriDic = {
        "tokenId":id,
        "name":"ASE(Arms of Spark Era) #"+ str(id),
        "image":ase.getImageUrl(),
        "detailimage":ase.getImageUrl(),
        "description":ase.getDesc(),
        "attributes":[
            {            
            "trait_type":"Arms Name",
            "value":ase.getName()
            },
            {            
            "trait_type":"Rank",
            "value":ase.getRank()
            },
            {            
            "trait_type":"Power",
            "value":ase.getPower()
            },
            {            
            "trait_type":"Endurance",
            "value":ase.getEndurance(),
            },
            {            
            "trait_type":"Strength",
            "value":ase.getStrength(),
            },
            {            
            "trait_type":"Agility",
            "value":ase.getAgility(),
            },
            {            
            "trait_type":"Armor",
            "value":ase.getArmor(),
            },
            {            
            "trait_type":"Crit",
            "value":ase.getCrit(),
            },
            {            
            "trait_type":"Pisonic",
            "value":ase.getPisonic(),
            },
            {            
            "trait_type":"Spirit",
            "value":ase.getSpirit(),
            },
            
            ]

    }
    return JSONResponse(content=jsonable_encoder(uriDic))