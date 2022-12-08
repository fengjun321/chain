import os
import shutil


workpath = []
def recursive_listdir(path):
    files = os.listdir(path)
    for file in files:
        file_path = os.path.join(path, file)

        if os.path.isfile(file_path):
            workpath.append(path)
            break

        elif os.path.isdir(file_path):
            if file_path.find('Thumbnails') == -1 and file_path.find('Marketplace')== -1:
                recursive_listdir(file_path)
    return workpath
def nameCorrection(path):
    thumrename(path)
    pngRename(path,1)
    pngRename(path,2)

def marketCpy(path,marketpath,marketfile):
    g = os.walk(path)
    for path2,dirlst,filelst in g:
        if path2.find('Thumbnail')!=-1:
            for filename in filelst:
                if filename.find('_head') == -1:
                    print(marketpath+'\\'+marketfile,"copy to ",path+'\\Thumbnail\\'+getHeadName(filename),"\n")
                    shutil.copyfile(marketpath+'\\'+marketfile,path+'\\Thumbnail\\'+getHeadName(filename))
                
def getHeadName(filename):
    name = filename.replace('.png','')+'_head.png'
    return name
def thumrename(path):
    g = os.walk(path)
    for path2,dirlst,filelst in g:
        #remove Thumbnails to Thumbnail
        if path2.find('Thumbnails')!=-1:
            print(path2,"rename to ",path2.strip('s'),"\n")
            os.rename(path2,path2.strip('s'))

def pngRename(path,phase):
    g = os.walk(path)
    for path2,dirlst,filelst in g:
        
        if path2.find('Marketplace')== -1 or path2.find('Thumbnail')!= -1:
            for filename in filelst:
                if os.path.isfile(path2+'\\'+filename):
                    newname = getNewname(filename)
                    if  newname != filename:
                        print(path2+'\\'+filename,"change to ",path2+'\\'+newname,"\n")
                        os.rename(path2+'\\'+filename,path2+'\\'+newname)
                    if filename.find('.psd') != -1:
                         print("remove ",path2+filename)
                         os.remove(path2+'\\'+filename)
        elif path2.find('Marketplace') != -1 and phase == 2:
            for filename in filelst:
                marketCpy(os.path.dirname(path2),path2,filename)


def getNewname(filename):
    name = filename
    if filename.find('head') == -1:
        name = name.replace('_','')
        name = name.replace('Card','')
        if name.find('Dark')== -1:
            name = name.replace('Iron','DarkIron')
    return name
paths = recursive_listdir('C:\\Users\\hzmhu\\Downloads\\HeroCard-20220103T171529Z-001\\HeroCard')
for p in paths:
    nameCorrection(p)