<#list traitList as trait>
  
     //begin of genPartByItemData
     function gen${trait.name}ItemData(uint8 value) public pure returns (uint256){
        uint256 r=value;
        r= r<< ${trait?counter*8};
     	return r;
     }
     //end of genPartByItemData
     
     
     
     <#if trait.standard>     
     //begin of genXXXValue
     function gen${trait.name}Value(uint256 nftId) public view returns (uint8){
     	uint16 rnd=rnd1( ${seed.next}+nftId,1000);
     	 <#list trait.items as item>
        	 <#if item?is_first >
			  <#else>
			 else
			 </#if>			  	
			 if(rnd>= ${item.low} && rnd < ${item.high}){
			 	return ${item.id};
			 }
        </#list>
        return 0;
     }
     //end of genXXXValue
     </#if>
     
     <#if trait.range>     
     //begin of genXXXValue
     function gen${trait.name}Value(uint256 nftId) public view returns (uint8){
     	uint16 rnd=rnd1( ${seed.next}+nftId,1000);
     	 <#list trait.items as item>
        	 <#if item?is_first >
			  <#else>
			 else
			 </#if>			  	
			 if(rnd>= ${item.low} && rnd < ${item.high}){			 		
			 	return uint8(rnd1( ${seed.next}+nftId,${item.range}) + ${item.rangeLow}) ;
			 }
        </#list>
        return 0;
     }
     //end of genXXXValue
     </#if>
     
     <#if trait.optional>     
     //begin of genXXXValue
     function gen${trait.name}Value(uint256 nftId) public view returns (uint8){
     	uint16 rnd=rnd1( ${seed.next}+nftId,1000);
     	 <#list trait.items as item>
        	 <#if item?is_first >
			  <#else>
			 else
			 </#if>			  	
			 if(rnd>= ${item.low} && rnd < ${item.high}){			 		
			 	return uint8( rnd1( ${seed.next}+nftId,${item.subList?size}) +1) ;
			 }
        </#list>
        return 0;
     }
     //end of genXXXValue
     </#if>
     
    
</#list>
