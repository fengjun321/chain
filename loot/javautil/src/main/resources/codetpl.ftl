<#list packList as pack>
  
     function ${pack.packName}() private view returns (uint256) {
        uint256 campValue = rnd1( ${seed.next},4);
		uint256 matValue=0;
		uint16 rnd=rnd1( ${seed.next},1000);
		//mat sec
		 <#list pack.materialRange as mat>
			 <#if mat?is_first >
			  <#else>
			 else
			 </#if>			  	
			 if(rnd >= ${mat.low} && rnd< ${mat.high}){
			 	matValue=${mat.name};
			 }
		 	</#list>
		 //mat sec end
		
		uint256 rarValue=0;
		uint256 itemTypeValue=0;
		uint256 itemIdValue=0;
		
		<#list campList as camp> 
		if(campValue==${camp.campName}){
		 	rnd=rnd1( ${seed.next},1000);		 	 
		 	//rarity sec 
		 	<#list pack.rarityRange as rar>
			  	<#if rar?is_first >
			   	<#else>
			  	else
			 	</#if>		  	
			  	if(rnd >= ${rar.low} && rnd < ${rar.high}){
			 		rarValue=${rar.name};
			  	}
		 	</#list>
		 	//rarity sec end
		 	
		 	<#list camp.rarities as rarity>
		 		<#if  rarity.availabelCount <= 0>
		 		if(rarValue == ${rarity.rarityString}){
		 		   revert("There is no item has ${rarity.rarityString} in this pack");
		 		}
		 		<#continue>
		 		</#if>
		 		if(rarValue == ${rarity.rarityString}){
			 		rnd=rnd1( ${seed.next},${rarity.availabelCount});		 		
			 		<#list rarity.items as item>
			 			<#if item?is_first >
					   	<#else>
					   	else
					   	</#if>
					   	if(rnd ==  ${item?index}){
					   		itemTypeValue = ${item.typeString};
					   		itemIdValue = rnd1(${seed.next},${item.count});
					   	}
			 		</#list>
		 		}		 					 	
		 	</#list>
		  }
		
		//end of camp
		</#list>		
         uint256 itemdata = buildItemDataV0(
                    itemIdValue,
                    itemTypeValue,
                    campValue,
                    rarValue,
                    matValue
                );
         return itemdata;		
     //end of function
    }
    
</#list>
