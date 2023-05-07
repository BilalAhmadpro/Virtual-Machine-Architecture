/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_project;

/**
 *
 * @author Bilal Ahmad
 */
public class SegmentTable {
    int limit;
    PageTable[] pagetable;
    SegmentTable(int lm,PageTable[] pt)
    {
        this.limit=lm;
        this.pagetable=pt;
    }
}
