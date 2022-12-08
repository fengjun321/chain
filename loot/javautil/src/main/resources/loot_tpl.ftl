<#list traitList as trait>
  
  	<#if trait.standard>
     //start of getcolor
     function get${trait.name}Color(uint16 value) public pure returns (string memory) {
        <#list trait.items as item>
        	 <#if item?is_first >
			  <#else>
			 else
			 </#if>			  	
			 if(value == ${item.id}){
			 	return ${item.color};
			 }
        </#list>
         revert("Illegal Value for ${trait.name}");
     }
     //end of getcolor
     </#if>
     
     <#if trait.range>
     //start of getcolor
     function get${trait.name}Color(uint16 value) public pure returns (string memory) {
        <#list trait.items as item>
        	 <#if item?is_first >
			  <#else>
			 else
			 </#if>			  	
			 if(value >= ${item.rangeLow} && value< ${item.rangeLow + item.range}){			 
			 	return ${item.color};
			 }
        </#list>
         revert("Illegal Value for ${trait.name}");
     }
     //end of getcolor
     </#if>
     
     <#if trait.optional>
     //start of getcolor
     function get${trait.name}Color(uint16 value) public pure returns (string memory) {
        <#list trait.items as item>
        	 <#if item?is_first >
			 	return ${item.color};
			 </#if>			  	
			
        </#list>
       
     }
     //end of getcolor
     </#if>
     
     
     <#if trait.standard>
     //start of getValueString
     function get${trait.name}ValueString(uint16 value) public  pure returns (string memory){
     	 <#list trait.items as item>
        	 <#if item?is_first >
			  <#else>
			 else
			 </#if>			  	
			 if(value == ${item.id}){
			 	return "${item.name}";
			 }
        </#list>   
         revert("Illegal Value for ${trait.name}");
     
     }     
     //end of getValueString
     </#if>
     
     <#if trait.optional>
     //start of getValueString
     function get${trait.name}ValueString(uint16 value) public  pure returns (string memory){
     	 <#list trait.items as item>
        	 <#list item.subList as sub>
        	 	 <#if item?is_first >
			     <#else>
			 	 else
			 	</#if>			  	
			 	if(value == ${sub?counter}){
			 		return "${sub}";
			 	}
        	 </#list>
        	 revert("Illegal Value for ${trait.name}");
        </#list>   
     
     }     
     //end of getValueString
     </#if>
     
     //begin of getPartByItemData
     function get${trait.name}ValueByItemData(uint256 itemData) public pure returns (uint8){
     	return uint8(itemData >>  ${trait?counter*8} ) & 0xFF;
     }
     //end of getPartByItemData
     
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
