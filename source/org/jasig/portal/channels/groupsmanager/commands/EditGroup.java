/**
 * Copyright � 2001 The JA-SIG Collaborative.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. Redistributions of any form whatsoever must retain the following
 *    acknowledgment:
 *    "This product includes software developed by the JA-SIG Collaborative
 *    (http://www.jasig.org/)."
 *
 * THIS SOFTWARE IS PROVIDED BY THE JA-SIG COLLABORATIVE "AS IS" AND ANY
 * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE JA-SIG COLLABORATIVE OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */


package  org.jasig.portal.channels.groupsmanager.commands;

/**
 * <p>Title: uPortal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Columbia University</p>
 * @author Don Fracapane
 * @version 2.0
 */
import java.util.*;
import org.jasig.portal.*;
import org.jasig.portal.channels.groupsmanager.*;
import org.jasig.portal.groups.*;
import org.jasig.portal.concurrency.*;
import org.jasig.portal.concurrency.caching.*;
import org.jasig.portal.concurrency.locking.*;
import org.jasig.portal.services.*;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

/** This command delegates to the GroupsService to find entities requested
 *  by the user.
 */
public class EditGroup extends GroupsManagerCommand {

   /**
    * put your documentation comment here
    */
   public EditGroup () {
   }

   /**
    * put your documentation comment here
    * @param sessionData
    */
   public void execute (CGroupsManagerSessionData sessionData) {
      ChannelStaticData staticData = sessionData.staticData;
      ChannelRuntimeData runtimeData= sessionData.runtimeData;

      Utility.logMessage("DEBUG", "EditGroup::execute(): Start");
      String parentElemId = getCommandArg(runtimeData);
      // if not IPerson group, then set view root to root for requested type
      try{
         String userID = getUserID(sessionData);
         String userName = GroupsManagerXML.getEntityName(ENTITY_CLASSNAME, userID);
         String lockKey = userID + "::" + userName;
         Element parentElem = GroupsManagerXML.getElementById(this.getXmlDoc(sessionData),parentElemId);
         String parentKey = parentElem.getAttribute("key");
         ILockableEntityGroup lockedGroup = GroupService.findLockableGroup(parentKey, lockKey);
         if (lockedGroup != null){
            // refresh element
            //    where does this method go...xml,utility,wrapper AND pull out elem caching
            // store in sessionData
            sessionData.lockedGroup=lockedGroup;
            sessionData.mode = EDIT_MODE;
            staticData.setParameter("groupParentId", parentElemId);
         }
         else{
            // need to display group name
            String msg = "Unable to aquire lock for group: " + parentKey;
            runtimeData.setParameter("cmdResponse", msg);
         }
      }
      catch(Exception e){
        LogService.instance().log(LogService.ERROR,e);
      }
      Utility.logMessage("DEBUG", "EditGroup::execute(): Uid of parent element = " +
            parentElemId);
   }
}



