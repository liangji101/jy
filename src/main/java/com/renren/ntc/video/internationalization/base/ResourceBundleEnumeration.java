package com.renren.ntc.video.internationalization.base;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ResourceBundleEnumeration
  implements Enumeration<String>
{
  Set<String> set;
  Iterator<String> iterator;
  Enumeration<String> enumeration;
  String next = null;

  public ResourceBundleEnumeration(Set<String> paramSet, Enumeration<String> paramEnumeration)
  {
    this.set = paramSet;
    this.iterator = paramSet.iterator();
    this.enumeration = paramEnumeration;
  }

  public boolean hasMoreElements()
  {
    if (this.next == null)
      if (this.iterator.hasNext())
        this.next = ((String)this.iterator.next());
      else 
    	  while (true) {
          do { if ((this.enumeration == null) || 
              (this.next != null) || (!(this.enumeration.hasMoreElements())))
//        	    break label104;
        	   break;
            this.next = ((String)this.enumeration.nextElement()); }
          while (!(this.set.contains(this.next)));
//          this.next = null; ////???
        }


    return (this.next != null);
//    label104: return (this.next != null);
  }

  public String nextElement() {
    if (hasMoreElements()) {
      String str = this.next;
      this.next = null;
      return str;
    }
    throw new NoSuchElementException();
  }
}
