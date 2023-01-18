package com.etnetera.hr.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "version"})})
@Data
@AllArgsConstructor
@Builder
public class JavaScriptFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column
    private String version;

    @Column
    private Date depricationDate;

    @Column
    private Integer hypeLevel;

    public JavaScriptFramework() {
    }
}
